#ifdef GL_ES
precision mediump float;
#endif


varying vec4 v_color;
varying vec2 v_texCoord0;

uniform vec2 u_resolution;

uniform float iMouseX[10];
uniform float iMouseY[10];
uniform float iGlobalTime[10];
uniform sampler2D u_sampler2D;

const float dist = 60.0;
const float speed = 10.0;
const float sd = speed/dist; 

float doRipple(float len,float time){
    if (sd*time > len){
//    float coef =  0.03;
	  float coef =  exp(-2.5*time)/2.0;
//    float coef = exp(-time/len)*3.0;

   
    return sin(len*dist-(time*speed))*coef;
    }else {
    return 0.0;
    }

}
void main(){	
	vec2 uv =  gl_FragCoord.xy / u_resolution;
	vec2 uv2 = vec2(0.0);
	for(int i = 0; i < 10 ;i++){
		float f = iGlobalTime[i];
		if (f > 0.0){
			uv2 += uv * doRipple(length(uv - vec2(iMouseX[i],iMouseY[i])/ u_resolution.xy),f); 
		}
    }   
	gl_FragColor = texture2D(u_sampler2D, uv + uv2);
	
}