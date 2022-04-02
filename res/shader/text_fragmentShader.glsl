#version 430

in vec3 color;
in vec2 fragTexCoord;
in float charID;

out vec4 finalColor;

layout(location = 10) uniform sampler2D atlas;
layout(location = 25) uniform float maxChars;
layout(location = 26) uniform float writerProgess;

void main() {
    vec4 texColor = texture(atlas, fragTexCoord);
    if(texColor.a < 0.5 || writerProgess * maxChars < charID) discard;

    finalColor = vec4(texColor.rgb * color, 1);
}