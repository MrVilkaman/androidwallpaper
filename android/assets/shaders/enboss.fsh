 #ifdef GL_ES
precision mediump float;
#endif


varying vec2 v_texCoord0;
uniform vec2 v_resolution;
uniform sampler2D u_sampler2D;

void main(){
	vec2 onePixel = vec2(1.0/v_resolution.x, 1.0/v_resolution.y);
	vec2 texCoord = v_texCoord0;
	
	vec3 color;
	color = vec3(0.5);
	
	color += texture2D(u_sampler2D,texCoord - onePixel).rgb *5;
	color -= texture2D(u_sampler2D,texCoord + onePixel).rgb *5;
	
	
	color = vec3((color.r + color.g + color.b) / 3.0);
	
	gl_FragColor = vec4(color,1);
}