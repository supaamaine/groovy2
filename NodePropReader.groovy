#!/usr/bin/groovy
class NodePropReader{

  //First read the system environment to see if it is set
  def city
  def node_props
  def panzura
  def jenkins_nas

  def NodePropReader () {

    try {
      node_props = new ConfigSlurper().parse(new File("./node.groovy").toURL())
    }
    catch (FileNotFoundException e) {
      node_props = new ConfigSlurper().parse(new File("./buildSrc/src/main/groovy/node.groovy").toURL())
    }

    find_city_def()
    println "EB Location = ${city}"
    panzura = node_props.network_locations."${city}".panzura
    jenkins_nas = node_props.network_locations."${city}".jenkins_nas
    println "panzura = ${panzura}"
    println "jenkins_nas = ${jenkins_nas}"
  }

  def find_city_def() {

    if (city == null || city == ""){
      city = System.getenv("city")
    }

    //It was not set see if we can get it from the prperty file based on "COMPUTERNAME"
    if (city == null || city == ""){
      try {
        System.out.println ("Environment variable \"city\" was not detected checking \"COMPUTERNAME\"")
        city = find_city(System.getenv("COMPUTERNAME"))
      }
      catch (RuntimeException e){
        city == null
      }
    }

    //It was not set see if we can get it from the prperty file based on "node_name"
    if (city == null || city == ""){
      try {
        println ("Environment variable \"COMPUTERNAME\" was not detected checking \"node_name\"")
        node_name = System.getenv("node_name")
        city = find_city(node_name)
      }
      catch (RuntimeException e){
        println ("The environment variable \"node_name\" was not set.")
        println("\nPlease set the environment variable \"city\" to one of the following values.")
        node_props.network_locations.each { network_info ->
          println ("\t${network_info.value.city}")
        }
        println ("\n")
        throw new RuntimeException ("Could not determine which city you are in.")
      }
    }
    validate_city(city)
  }

  //******************************************************************************

  String find_city (String node_2_find) {
      // given a node name locate its eb location

    println ("node_2_find = ${node_2_find}")
    if (node_2_find == null || node_2_find == "") {
      throw new RuntimeException ("node_2_find was not defined.")
    }

    println ("node_2_find = ${node_2_find}")
    String return_city = node_2_find
    node_props.network_locations.each { network_info ->
      network_info.value.nodes.each{ node ->
        if (node == node_2_find) {
          return_city = network_info.value.city
        }
      }
    }
    if (return_city != node_2_find) {
      println ("${node_2_find} is located in ${return_city}.")
      return return_city
    }
    else {
      println ("Could not locate \"$node_2_find\".")
      throw new RuntimeException ("Node \"${node_2_find}\" was not located")
    }
  }

  //******************************************************************************

  boolean validate_city (String city_2_find) {

    // given a city name validate that it is set to one of the defined EB locations
    // defined in the node.groovy property file

    if (city_2_find == null || city_2_find == "") {
      throw new RuntimeException ("city_2_find was not defined.")
    }

    println ("Searching cities for \"$city_2_find\"")
    boolean return_value = false
    node_props.network_locations.each { network_info ->
        if (network_info.value.city == city_2_find) {
          return_value = true
        }
    }

    if (return_value == true) {
      println ("The city of \"${city_2_find}\" is defined in node.groovy")
      return return_value
    }
    else {
      println("\nPlease set the environment variable \"city\" to one of the following values.")
      network_locations.each { network_info ->
          println ("\t${network_info.value.city}")
      }
      throw new RuntimeException ("ERROR: the city of \"${city_2_find}\" is NOT defined in node.groovy.\n")
    }
  }
}
