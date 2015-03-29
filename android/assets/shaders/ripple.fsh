#ifdef GL_ES
precision mediump float;
#endif

#define N 50
varying vec4 v_color;
varying vec2 v_texCoord0;

uniform vec2 u_resolution;
uniform float[N] iMouseX;
uniform float[N] iMouseY;
uniform float[N] iGlobalTime;
uniform sampler2D u_sampler2D;



const float dist = 40.0;
const float speed = 16.0;


vec2 doRipple(vec2 pos, vec2 uv,float time){

	if (time <0){
		return vec2(0.0);
	}

    vec2 so = pos/ u_resolution.xy;
	vec2 pos2 = vec2(uv - so); 	  //wave origin
	vec2 pos2n = normalize(pos2);

	float len = length(pos2);
   
  //  float coef =  0.03;
    float coef =  exp(-time/len)*2.0;
      
    float ff = len*dist-(time*speed );
    vec2 uv2;
    
    float frontWave = speed * (time)/dist;
    if (frontWave > len){
    uv2 = uv*(sin(ff)*coef);
    }else {
    uv2 = uv *0.0;
    }
    return uv2; 
}
void main(){	
	vec2 uv =  gl_FragCoord.xy / u_resolution;
	vec2 uv2 = vec2(0.0);
	for(int i = 0;i<N ;i++){
    uv2 += doRipple(vec2(iMouseX[i],iMouseY[i]),uv,iGlobalTime[i]); 
    }   
 	uv2 +=uv;
	gl_FragColor = texture2D(u_sampler2D,uv2);
	
}