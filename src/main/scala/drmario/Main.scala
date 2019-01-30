package drmario

import scalafx.application.JFXApp
import scalafx.scene.Scene

object Main extends JFXApp {
  stage = new JFXApp.PrimaryStage {
    title = "Dr. Mario"
    scene = new Scene(8*30,16*30){
      
    }
  }
}