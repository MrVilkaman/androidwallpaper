#ifdef GL_ES
precision mediump float;
#endif


attribute vec4 a_color;
attribute vec4 a_position;
attribute vec2 a_texCoord0;

uniform mat4 u_projTrans;

varying mat4 v_projTrans;

varying vec4 v_color;
varying vec4 v_position;

void main(){
	v_color = a_color;
	v_projTrans = u_projTrans;
	v_position =  vec4(a_texCoord0,1.0,1.0);
	gl_Position =  u_projTrans * a_position;
}