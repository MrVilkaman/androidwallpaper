#ifdef GL_ES
precision mediump float;
#endif
#define N 10

varying vec2 v_position;
uniform vec2 u_resolution;

uniform float iMouseX[N];
uniform float iMouseY[N];
uniform float iGlobalTime[N];
uniform sampler2D u_sampler2D;


const float dist = 60.0;
const float speed = 15.0;
const float sd = speed/dist; 

float doRipple(float len,float time){
    if (sd*time > len){
    float coef =  exp(-3.4*time)/2.5;
     return sin(len*dist-(time*speed))*coef;
    }else {
    return 0.0;
    }

}
void main(){	
	vec2 uv2 = vec2(0.0);
	for(int i = 0; i < N ;i++){
		float f = iGlobalTime[i];
		if (f > 0.0){
			uv2 += v_position * doRipple(length(gl_FragCoord.xy / u_resolution - vec2(iMouseX[i],iMouseY[i])/ u_resolution.xy),f); 
		}
    }   
	gl_FragColor = texture2D(u_sampler2D, v_position + uv2);
	
}