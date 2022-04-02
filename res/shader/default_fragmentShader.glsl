#version 430

layout(location = 10) uniform sampler2D atlas;
layout(location = 21) uniform float hasBorder;
layout(location = 40) uniform vec3 color;

//import util_conditional

in vec2 fragTexCoord;
in vec2 cleanFragTex;

out vec4 finalColor;

void main() {
    vec4 texColor = texture(atlas, fragTexCoord);

    float shouldDiscard = max(texColor.a, hasBorder * when_gt(texColor.a, 0));

    if(shouldDiscard < 1) discard;

    float transparent = when_le(texColor.a, .99);
    texColor *= transparent * vec4(0.9, 0.8, 0, 1) + (1 - transparent) * vec4(1, 1, 1, 1);
    texColor.a = 1;

    finalColor = texColor;
}