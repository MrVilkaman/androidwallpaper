#ifdef GL_ES
precision mediump float;
#endif


attribute vec4 a_position;
attribute vec2 a_texCoord0;

uniform mat4 u_projTrans;
varying vec2 v_position;

void main(){

	v_position =  a_texCoord0;//vec4(,1.0,1.0);
	gl_Position =  u_projTrans * a_position;
}