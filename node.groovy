
bothell_panzura = "\\\\usbth1fs003\\FSG3\$"
farmington_panzura = "\\\\usfhi1fs003\\FSG3\$"
erlangen_panzura ="\\\\denue6fs007\\FSG3\$"
jenkins_nas = "\\\\usbth1fas02\\fsg3_build_data\$\\Jenkins"

network_locations  {
  bothell  {
    city = "bothell"
    panzura = "${bothell_panzura}"
    jenkins_nas = "${jenkins_nas}"
    nodes  = ["USD000167", "USD000168", "USD000180",
              "USD000181", "USD000151", "master",
              "VM168-01", "VM168-02", "VM168-03", "VM168-04",
              "VM168-05", "VM168-06", "VM168-07", "VM168-08",
              "VM180-01", "VM180-02", "VM180-03", "VM180-04",
              "VM180-05", "VM180-06", "VM180-07", "VM180-08",
              "VM181-01", "VM181-02", "VM181-03", "VM181-04",
              "VM181-05", "VM181-06", "VM181-07", "VM181-08"]
  }

  farmington  {
    city = "farmington"
    panzura = "${farmington_panzura}"
    jenkins_nas = "${jenkins_nas}"
    nodes = ["USD000164"]
  }

  erlangen  {
    city="erlangen"
    panzura = "${erlangen_panzura}"
    jenkins_nas = "${jenkins_nas}"
    nodes = ["B20083", "B20095", "B20096", "B20097", "B20098"]
  }

  oulu  {
    city="oulu"
    panzura = "${erlangen_panzura}"
    jenkins_nas = "${jenkins_nas}"
    nodes = ["Oulu_HW_Node", "Oulu_HW_Node2", "Oulu_VM1", "Oulu_VM2"]
  }
}
