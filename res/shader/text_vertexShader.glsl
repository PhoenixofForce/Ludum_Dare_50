#version 430

//layout(location = 0) uniform mat4 trans;

layout(location = 0) in vec4 cPosition;
layout(location = 1) in vec2 cTexCoord;
layout(location = 2) in vec4 posOffset;
layout(location = 3) in vec4 spriteSheetBounds;
layout(location = 4) in vec3 inColor;
layout(location = 5) in float charIndex;
layout(location = 6) in float wobbleStrength;

layout(location = 10) uniform sampler2D atlas;
layout(location = 20) uniform float translationX;
layout(location = 21) uniform float translationY;
layout(location = 22) uniform float windowWidth;
layout(location = 23) uniform float windowHeight;
layout(location = 24) uniform float ticks;
layout(location = 25) uniform float maxChars;
layout(location = 26) uniform float writerProgess;

out vec3 color;
out vec2 fragTexCoord;
out float charID;

vec2 atlasUv(vec2 uv, sampler2D spriteSheet, vec4 bounds, float flipY) {
    vec2 atlasSize = textureSize(spriteSheet, 0);
    vec2 range = bounds.zw;
    vec2 texPos = (vec2(uv.x, flipY * (1 - uv.y) + (1 - flipY) * (uv.y)) * range + bounds.xy);
    texPos = vec2(texPos.x / atlasSize.x, texPos.y / atlasSize.y);
    return texPos;
}

void main()
{

    float wobble = sin((ticks + charIndex * 2) * 0.1f) * wobbleStrength;

    vec2 offset = vec2(posOffset.x / windowWidth, posOffset.y / windowHeight);

    mat4 transformation = mat4(
        posOffset.z / windowWidth, 0, 0, 0,
        0, posOffset.w / windowHeight, 0, 0,
        0, 0, 1, 0,
        offset.x + translationX, offset.y + translationY, 0, 1
    );


    gl_Position = transformation * cPosition + vec4(0, wobble, 0, 0);

    fragTexCoord = atlasUv(cTexCoord, atlas, spriteSheetBounds, 1);

    charID = charIndex;
    color = inColor;
}