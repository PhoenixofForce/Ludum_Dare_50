#version 430

layout(location = 0) uniform mat4 projection;
layout(location = 1) uniform mat4 view;
layout(location = 2) uniform mat4 transformation;

layout(location = 20) uniform float flipXAtlas;
layout(location = 21) uniform float flipYAtlas;

layout(location = 10) uniform sampler2D atlas;
layout(location = 60) uniform vec4 textureBounds;

layout(location = 0) in vec4 cPosition;
layout(location = 1) in vec2 cTexCoord;
layout(location = 2) in vec3 vNormal;

out vec2 fragTexCoord;
out vec2 cleanFragTex;

vec2 atlasUv(vec2 uv, sampler2D spriteSheet, vec4 bounds, float flipX, float flipY) {
    vec2 atlasSize = textureSize(spriteSheet, 0);
    vec2 range = bounds.zw;
    vec2 texPos = (vec2(flipX * (1 - uv.x) + (1 - flipX) * (uv.x), flipY * (1 - uv.y) + (1 - flipY) * (uv.y)) * range + bounds.xy);
    texPos = vec2(texPos.x / atlasSize.x, texPos.y / atlasSize.y);
    return texPos;
}

void main()
{
    gl_Position = projection * view * transformation * cPosition;
    fragTexCoord = atlasUv(cTexCoord, atlas, textureBounds, flipXAtlas, flipYAtlas);
    cleanFragTex = cTexCoord;
}