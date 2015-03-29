#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoord0;

uniform vec2 u_resolution;
uniform sampler2D u_sampler2D;

const float radius = .5, innerRadius = .4, intensive = .7; 

void main(){
	vec4 color = texture2D(u_sampler2D,v_texCoord0) * v_color;
		
	vec2 relativePos = gl_FragCoord.xy / u_resolution - .5 	;	
	float len = length(relativePos);
	float vignette = smoothstep(radius, innerRadius,len);
	
	color.rgb = mix(color.rgb, color.rgb * vignette,intensive);
	gl_FragColor = color;
}