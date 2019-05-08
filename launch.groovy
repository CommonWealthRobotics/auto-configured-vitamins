
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

// load the robot
def base =DeviceManager.getSpecificDevice( "HephaestusArm",{
	return ScriptingEngine.gitScriptRun(
            "https://github.com/CommonWealthRobotics/auto-configured-vitamins.git", // git location of the library
            "loadRobot.groovy" , // file to load
            null
            )
})
double indicator = 0
while((indicator =MobileBaseCadManager.get( base).getProcesIndictor().get())<1){
	println "Waiting for cad to get to 1:"+indicator
	ThreadUtil.wait(1000)
}
java.lang.reflect.Type TT_mapStringString = new TypeToken<HashMap<String,HashMap<String,HashMap<String,Object>>>>() {
  }.getType();
  //chreat the gson object, this is the parsing factory
Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
HashMap<String,HashMap<String,HashMap<String,Object>>> limbData = new HashMap<>()

for(def limb:base.getAllDHChains() ){
	String limbName =limb.getScriptingName()
	ArrayList<DHLink>  chain = limb.getChain().getLinks()
	HashMap<String,HashMap<String,Object>> linkData = new HashMap<>()
	for(int i=0;i<chain.size();i++){
		DHLink dh = chain.get(i)
		// Hardware to engineering units configuration
		LinkConfiguration conf = limb.getLinkConfiguration(i);
		String linkName = conf.getName()
		HashMap<String,Object> data =new HashMap<>()
		data.put("motorType",conf.getElectroMechanicalType())
		data.put("motorSize", conf.getElectroMechanicalSize() )
		data.put("shaftType",conf.getShaftType())
		data.put("shaftSize",conf.getShaftSize())
		data.put("dh-D",dh.getD())
		data.put("dh-A",dh.getR())
		data.put("dh-Alpha",Math.toDegrees(dh.getAlpha()))
		data.put("dh-Theta",Math.toDegrees(dh.getTheta()))
		linkData.put(linkName,data)
	}
	limbData.put(limbName,linkData)
}
String json =  gson.toJson(limbData, TT_mapStringString)
println json


MobileBaseCadManager manager = MobileBaseCadManager.get(base)
manager.generateCad()
ThreadUtil.wait(1000)
indicator = 0
while((indicator =MobileBaseCadManager.get( base).getProcesIndictor().get())<1){
	println "Re-loading..."+indicator
	ThreadUtil.wait(1000)
}
