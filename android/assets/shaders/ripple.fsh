#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoord0;

uniform vec2 u_resolution;
uniform vec2 iMouse;
uniform sampler2D u_sampler2D;
uniform float iGlobalTime;

void main(){	
	vec2 uv =  gl_FragCoord.xy / u_resolution;
	
	gl_FragColor = texture2D(u_sampler2D,uv);
	
}