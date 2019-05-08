// load the robot
def base =DeviceManager.getSpecificDevice( "HephaestusArm",{
	return ScriptingEngine.gitScriptRun(
            "https://github.com/madhephaestus/SeriesElasticActuator.git", // git location of the library
            "FullHardwareLaunch.groovy" , // file to load
            null
            )
})
double indicator = 0
while((indicator =MobileBaseCadManager.get( base).getProcesIndictor().get())<1){
	println "Waiting for cad to get to 1:"+indicator
	ThreadUtil.wait(1000)
}
