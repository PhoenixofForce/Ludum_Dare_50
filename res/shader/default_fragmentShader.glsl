#version 430

layout(location = 10) uniform sampler2D atlas;
layout(location = 40) uniform vec3 color;

in vec2 fragTexCoord;

out vec4 finalColor;

void main() {
    vec4 texColor = texture(atlas, fragTexCoord);
    if(texColor.a < 1) discard;
    texColor.a = 1;

    finalColor = texColor;
}