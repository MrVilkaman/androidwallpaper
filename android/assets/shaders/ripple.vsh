#ifdef GL_ES
#define LOWP lowp
precision lowp float;
precision lowp int;
precision lowp sampler2D;
precision lowp samplerCube;
#else
#define LOWP
#endif

attribute LOWP vec4 a_color;
attribute LOWP vec4 a_position;
attribute LOWP vec2 a_texCoord0;

uniform LOWP mat4 u_projTrans;
varying LOWP vec2 v_position;
varying LOWP vec4 v_color;

void main(){
	v_color = a_color;
	v_position =  a_texCoord0;//vec4(,1.0,1.0);
	gl_Position =  u_projTrans * a_position;
}