


class Run{

  def command (cmd_line){
    //runs generic python script
    def out = new StringBuffer()
    def err = new StringBuffer()


    println ("\t" + cmd_line + "\n")
    Process p = cmd_line.execute()
    p.consumeProcessOutput( out, err )
    p.waitFor()


    if( out.size() > 0 ) {print ("\t" + out + "\n")}
    if( err.size() > 0 ) {print ("\t" + err + "\n")}

    if (p.exitValue() != 0 ){
        def msg = "command " + cmd_line + "\n" +
                  "returned an error code " + p.exitValue() + "\n"
        throw new Exception(msg)
    }
  }
}
