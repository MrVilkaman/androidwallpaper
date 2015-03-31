#ifdef GL_ES
#define LOWP lowp
precision lowp float;
precision lowp int;
precision lowp sampler2D;
precision lowp samplerCube;
#else
#define LOWP
#endif

#define N 10

varying LOWP vec4 v_color;
varying LOWP vec2 v_position;
uniform LOWP vec2 u_resolution;

uniform LOWP float iMouseX[N];
uniform LOWP float iMouseY[N];
uniform LOWP float iGlobalTime[N];
uniform LOWP sampler2D u_sampler2D;


const LOWP float dist = 60.0;
const LOWP float speed = 15.0;
const LOWP float sd = speed/dist; 

float doRipple(float len,float time){
    if (sd*time > len){
    LOWP float coef =  exp(-3.4*time)/2.5;
     return sin(len*dist-(time*speed))*coef;
    }else {
    return 0.0;
    }

}
void main(){	
	LOWP vec2 uv2 = vec2(0.0);
	for(int i = 0; i < N ;i++){
		LOWP float f = iGlobalTime[i];
		if (f > 0.0){
			uv2 += v_position * doRipple(length((gl_FragCoord.xy - vec2(iMouseX[i],iMouseY[i]))/ u_resolution.xx),f); 
		}
    }   
	gl_FragColor = v_color * texture2D(u_sampler2D, v_position + uv2);
	
}