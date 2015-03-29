#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoord0;

uniform vec2 u_resolution;
uniform float iMouseX;
uniform float iMouseY;
uniform sampler2D u_sampler2D;
uniform float iGlobalTime;


const float dist = 40.0;
const float speed = 12.0;


vec2 doRipple(vec2 pos, vec2 uv){

    vec2 so = pos/ u_resolution.xy;
	vec2 pos2 = vec2(uv - so); 	  //wave origin
	vec2 pos2n = normalize(pos2);

	float len = length(pos2);
   
  //  float coef =  0.03;
    float coef =  exp(-iGlobalTime/len)*2.0;
      
    float ff = len*dist-(iGlobalTime*speed );
    vec2 uv2;
    
    float frontWave = speed * (iGlobalTime)/dist;
    if (frontWave > len){
    uv2 = uv*(sin(ff)*coef);
    }else {
    uv2 = uv *0.0;
    }
    return uv2; 
}
void main(){	
	vec2 uv =  gl_FragCoord.xy / u_resolution;
	vec2 pos = vec2(iMouseX,iMouseY);
    vec2 uv2 = doRipple(pos,uv); 
       
 	uv2 +=uv;
	gl_FragColor = texture2D(u_sampler2D,uv2);
	
}