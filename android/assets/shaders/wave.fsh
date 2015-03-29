#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoord0;

uniform vec2 u_resolution;
uniform vec2 iMouse;
uniform sampler2D u_sampler2D;
uniform float iGlobalTime;

const float PI = 3.14159265359;
float time;

const vec3 eps = vec3(0.01, 0.0, 0.0);

float genWave1(float len)
{
	float wave = sin(8.0 * PI * len + time);
	wave = (wave + 1.0) * 0.5; // <0 ; 1>
	wave -= 0.3;
	wave *= wave * wave;
	return wave;
}

void main(){	
    time = -iGlobalTime*5.0;

	vec2 uv =  gl_FragCoord.xy / u_resolution;
	vec2 so = iMouse.xy / u_resolution;
	vec2 pos2 = vec2(uv - so); 	  //wave origin
	vec2 pos2n = normalize(pos2);
	
	float len = length(pos2);
	
	float speed = 8.0 * PI * len ;
	float wave = sin(speed + time);
	wave = (wave + 1.0) * 0.4; // <0 ; 1>
	wave -= 0.25;
	wave *= wave * wave;
	
	float ex = exp(0.4*(speed+time));;
        if(ex > 0.9999 ){
        ex = 0.0 ;
        }
        
    wave *= ex;
	
	vec2 uv2 = -pos2n * wave/(1.0 + 5.0 * len);
//(uv + uv2)
	vec4 color = texture2D(u_sampler2D,(uv+ uv2));
	//vec4 color = texture2D(u_sampler2D,v_texCoord0);
	//color.r = normalize(uv + uv2).x;
//	gl_FragCoord = vec2(v_texCoord0.x,v_texCoord0.y+50;
//	gl_FragColor = texture2D(u_sampler2D,v_texCoord0) ;
	gl_FragColor = color ;
	
}