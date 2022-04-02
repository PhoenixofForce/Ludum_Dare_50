package window;

import gameobjects.entities.Camera;
import gameobjects.particles.ParticleSpawner;
import map.GameMap;
import meshes.loader.AssetLoader;
import meshes.ScreenRect;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import rendering.ShaderHandler;
import utils.Constants;
import utils.Options;
import utils.TimeUtils;
import window.font.TextureAtlasFont;
import window.gui.*;
import window.gui.listener.MouseClickListener;
import window.inputs.InputHandler;

import java.nio.*;
import java.util.Arrays;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window extends BasicColorGuiElement {

	public static Window INSTANCE;

	// The window handle
	public long window;
	private int width = 960;
	private int height = 600;

	private final String title = "This is an engine!";

	private Camera cam;
	private GameMap map;

	public Window() {
		super(null, 0, 0, 0, 0);
	}

	public void run() {
		INSTANCE = this;

		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		init();
		loop();
		cleanUp();
	}

	private void init() {
		GLFWErrorCallback.createPrint(System.err).set();

		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(width, height, title, NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		initCallbacks();

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
					window,
					(vidmode.width() - pWidth.get(0)) / 2,
					(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		glfwMakeContextCurrent(window);
		glfwSwapInterval(Options.useVsync? 1: 0);			//vsync on

		GL.createCapabilities();
		glEnable(GL_DEPTH_TEST);
		glDisable(GL_CULL_FACE);
		glDisable(GL_BLEND);
		glDepthFunc(GL_LEQUAL);

		ShaderHandler.initShader();
		AssetLoader.loadAll();
		loadGui();

		glfwShowWindow(window);
		glClearColor(46.0f / 255.0f, 34.0f / 255.0f, 47.0f / 255.0f, 1);

		cam = new Camera();
		map = new GameMap();
		map.init();
	}

	private float[] clickStart;
	private void initCallbacks() {
		InputHandler.callbacks();

		glfwSetMouseButtonCallback(window, (window, button, action, mods) -> {
			if(action == GLFW_PRESS) {
				clickStart = InputHandler.getMousePosition();
				clickStart = handleMouseButton(action, button, clickStart[0], clickStart[1]);
			} else {
				handleMouseButton(action, button, clickStart[0], clickStart[1]);
			}


			if(action == GLFW_RELEASE) {
				clickStart = new float[]{-2f, -2f};
			}
		});

		glfwSetWindowSizeCallback(window, (window, width, height) -> {
			this.width = width;
			this.height = height;
			glViewport(-1, -1, width, height);
		});

		glfwSetErrorCallback((e, f) -> {
			System.err.println(e + " " + f);
		});
	}

	private void loop() {
		long lastUpdate = TimeUtils.getTime();
		long lastUpdateNano = TimeUtils.getNanoTime();
		while ( !glfwWindowShouldClose(window) ) {
			long dt = TimeUtils.getTime() - lastUpdate;
			long dtNano = TimeUtils.getNanoTime() - lastUpdateNano;
			lastUpdate = TimeUtils.getTime();
			lastUpdateNano = TimeUtils.getNanoTime();

			if(dt > 0) glfwSetWindowTitle(window, title + " (" + (int)(Math.ceil(1000000000.0 / dtNano)) + ")");
			
			testOpenGLError();
			glfwPollEvents();

			input();
			update(dt);
			render();
		}
	}

	private void input() {
		InputHandler.update();
	}

	private void update(long dt) {
		if(text != null) {
			text.clear().addText("(" + cam.getPosition().x + " | " + cam.getPosition().y +  ")").build();
		}

		updateGui(dt);
		ParticleSpawner.updateAll(dt);
		cam.update(dt);
		map.update(dt);

		Constants.RUNTIME++;
	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		Matrix4f projection_matrix = new Matrix4f().perspective((float)Math.PI/3, ((float) width)/height,0.001f, 1250f);
		Matrix4f view_matrix = new Matrix4f().lookAt(cam.getPosition(), new Vector3f(cam.getLookingDirection()).add(cam.getPosition()), cam.getUp());

		map.render(projection_matrix, view_matrix);
		ParticleSpawner.renderAll(projection_matrix, view_matrix);
		super.renderGui();

		glfwSwapBuffers(window);
	}

	private void cleanUp() {
		map.cleanUp();

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();

		ShaderHandler.cleanup();
		AssetLoader.unloadAll();
		ScreenRect.getInstance().cleanUp();
	}

	public void testOpenGLError() {
		int errorCode = glGetError();
		if (errorCode != GL_NO_ERROR) {
			if(errorCode == GL_INVALID_ENUM) throw new RuntimeException("Invalid Enumereraion (" + errorCode + ")");
			else if(errorCode == GL_INVALID_VALUE) throw new RuntimeException("Invalid Value (" + errorCode + ")");
			else if(errorCode == GL_INVALID_OPERATION) throw new RuntimeException("Invalid Operation (" + errorCode + ")");
			else if(errorCode == GL_STACK_OVERFLOW) throw new RuntimeException("Stack Overflow (" + errorCode + ")");
			else if(errorCode == GL_STACK_UNDERFLOW) throw new RuntimeException("Stack Underflow (" + errorCode + ")");
			else if(errorCode == GL_OUT_OF_MEMORY) throw new RuntimeException("Out of Memory (" + errorCode + ")");
			else if(errorCode == GL_INVALID_FRAMEBUFFER_OPERATION) throw new RuntimeException("Invalid Framebuffer Operation (" + errorCode + ")");
			else if(errorCode == GL_CONTEXT_LOST) throw new RuntimeException("Context Lost (" + errorCode + ")");
		}
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public float getHeight() {
		return height;
	}

	private GuiText text;
	public void loadGui() {
		this.addClickListener((event, button) -> {
			if(event == GLFW_PRESS) {
				float[] mapClick = translateToMapSpace(clickStart[0], clickStart[1]);

				map.handleClick(mapClick[0], mapClick[1]);
			}
		});


		GuiButton button_left = new GuiButton(this, Anchor.BOTTOM_LEFT, 20, 20, 100, 100, "arrow_left");
		GuiButton button_right = new GuiButton(this, Anchor.BOTTOM_RIGHT, -20, 20, 100, 100, "arrow_right");

		button_left.addClickListener((event, button) -> {
			cam.getInputProvider().setMoveLeft(event != GLFW_RELEASE);
		});

		button_right.addClickListener((event, button) -> {
			cam.getInputProvider().setMoveRight(event != GLFW_RELEASE);
		});

		text = new GuiText(this, Anchor.TOP_LEFT, 20, -20, new TextureAtlasFont("Font"), 16).addText("test").build();
	}

	public float[] translateToMapSpace(float x, float y) {
		float clickX = (x / width) * 2 - 1;
		float clickY = (y / height) * 2 - 1;

		clickX = -clickX * 2.25f + cam.getPosition().x;
		clickY = clickY * 1.25f + cam.getPosition().y;

		return new float[]{clickX, clickY};
	}
}