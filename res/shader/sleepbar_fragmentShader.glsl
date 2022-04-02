#version 430

layout(location = 10) uniform sampler2D atlas;
layout(location = 21) uniform float sleepValue;
layout(location = 61) uniform vec4 maskLocation;

//import util_conditional

in vec2 fragTexCoord;
in vec2 cleanFragTex;

out vec4 finalColor;

vec2 atlasUv(vec2 uv, sampler2D spriteSheet, vec4 bounds, float flipY) {
    vec2 atlasSize = textureSize(spriteSheet, 0);
    vec2 range = bounds.zw;
    vec2 texPos = (vec2(uv.x, flipY * (1 - uv.y) + (1 - flipY) * (uv.y)) * range + bounds.xy);
    texPos = vec2(texPos.x / atlasSize.x, texPos.y / atlasSize.y);
    return texPos;
}

void main() {

    vec4 texColor = texture(atlas, fragTexCoord);
    float maskValue = texture(atlas, atlasUv(cleanFragTex, atlas, maskLocation, 1)).a;

    if(texColor.a < 1) discard;

    //maskValue > sleepValue
    float isLessThanMask = 1 - when_gt(maskValue, sleepValue);

    finalColor = texColor * isLessThanMask + (1 - isLessThanMask) * vec4(46.0 / 255.0, 34.0 / 255.0, 47.0 / 255.0, 1);
}