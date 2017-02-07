import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;

import static java.lang.Math.random;

/**
 * Himanshu Chaudhary
 *
 * This class has all GUI elements that needs to be displayed. There are functions which is used to create GUI
 * The animation is modified versison of Animatoion from Java Fx Tutorial with spheres added
 * The Start/Stop pauses and plays the animation
 * Reset button resets the animation with the user defined values of number of circles and spheres. Start button needs to pressed for the animation to
 *    start again.
 */

public class WidgetsWithAnimation extends Application
{
  Button btn = new Button("Start Animation");    //elements that needs to be displayed in GUI
  Button resetBtn = new Button("Reset");          // Reset Button reloads animation with user-defined values
  SplitPane animation = new SplitPane();
  GridPane userElements = new GridPane();
  BorderPane root = new BorderPane();
  Timeline timeline = new Timeline();
  Label circleLabel = new Label("Number of Circles");
  Label sphereLabel = new Label("Number of Sphere");

  ComboBox<Integer> circlesNum = new ComboBox();     // Combobox will allow users to select numbers of circles and spheres
  ComboBox<Integer> spheresNum = new ComboBox();


  Scene scene = new Scene(root, 700, 700);

  /**\
   * @param primaryStage
   * @throws Exception
   *
   * This function sets the scene to the primarystage. The scene contains two parts, animaton and button layout. Both are added to a borderpane
   * The animations can be stopped and regenerated with user-defined values at any time
   */
  @Override
  public void start(Stage primaryStage) throws Exception
  {


    primaryStage.setTitle("Widgets with Animation");


    circlesNum.getItems().addAll(10, 20, 30, 40, 50);
    spheresNum.getItems().addAll(0, 10, 50, 100, 200, 500);
    createAnimation();
    primaryStage.setScene(scene);


    primaryStage.show();

    btn.setOnAction(e ->
    {
      String btnText = btn.getText();
      if (btnText.equals("Start Animation") || btnText.equals("Resume Animation"))
      {
        timeline.play();
        btn.setText("Stop Animation");

      } else if (btn.getText() == "Stop Animation")
      {
        timeline.pause();
        btn.setText("Resume Animation");  // Once started button text says Resume Animation

      }


    });
    resetBtn.setOnAction(e ->
    {
      timeline.stop();
      createAnimation();   // Reloads the GUI with user-set values or Default values
      btn.setText("Start Animation");

    });

  }

  /**
   * This method generates the button layout with options to start, reset, and imput from combobox
   */
  void generateButtonLayout()
  {
    userElements = new GridPane();
    userElements.setAlignment(Pos.CENTER);
    userElements.setHgap(10);
    userElements.setVgap(10);
    userElements.setPadding(new Insets(25, 25, 25, 25));
    userElements.add(btn, 0, 0);
    userElements.add(resetBtn, 1, 0);
    userElements.add(circleLabel, 2, 0);
    userElements.add(sphereLabel, 4, 0);
    userElements.add(circlesNum, 3, 0);
    userElements.add(spheresNum, 5, 0);

  }

  /**
   * Some part of code is taken from Oracle Java FX tutorial and modified
   *     source: http://docs.oracle.com/javafx/2/get_started/animation.htm
   *  This method creates the GUI with circles and spheres in different groups
   *  and then blends them to display an animation of 40 second
   */

  private void createAnimation()
  {
    Group circles = new Group();
    int numCircles = (circlesNum.getSelectionModel().getSelectedIndex() == -1) ? 30 : circlesNum.getValue(); //sets default value or user defined value
    int numSpheres = (spheresNum.getSelectionModel().getSelectedIndex() == -1) ? 30 : spheresNum.getValue();
    for (int i = 0; i < numCircles; i++)
    {
      Circle circle = new Circle(150, Color.web("white", 0.05));
      circle.setStrokeType(StrokeType.OUTSIDE);
      circle.setStroke(Color.web("white", 0.16));
      circle.setStrokeWidth(4);
      circles.getChildren().add(circle);
    }

    for (int i = 0; i < numSpheres; i++)
    {
      Sphere sphere = new Sphere(5, 4);
      circles.getChildren().add(sphere);
    }


    Rectangle colors = new Rectangle(scene.getWidth(), scene.getHeight() - 100,
    new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE, new
    Stop[]{
    new Stop(0, Color.web("#f8bd55")),
    new Stop(0.14, Color.web("#c0fe56")),
    new Stop(0.28, Color.web("#5dfbc1")),
    new Stop(0.43, Color.web("#64c2f8")),
    new Stop(0.57, Color.web("#be4af7")),
    new Stop(0.71, Color.web("#ed5fc2")),
    new Stop(0.85, Color.web("#ef504c")),
    new Stop(1, Color.web("#f2660f")),}));  // Rectangle contain the colors at different stop locations

    Group blendModeGroup =
    new Group(new Group(new Rectangle(scene.getWidth(), scene.getHeight() - 100,
    Color.BLACK), circles), colors);  //blendModeGroup blends rectangle with circle so that the color changes when the circle moves
    colors.setBlendMode(BlendMode.OVERLAY);
    root.getChildren().add(blendModeGroup);  //BlendModeGroup is added to the root panel

    for (Node circle : circles.getChildren())
    {
      timeline.getKeyFrames().addAll(
      new KeyFrame(Duration.ZERO, // set start position at 0
      new KeyValue(circle.translateXProperty(), random() * 800),
      new KeyValue(circle.translateYProperty(), random() * 600)
      ),
      new KeyFrame(new Duration(40000), // set end position at 40s
      new KeyValue(circle.translateXProperty(), random() * 800),
      new KeyValue(circle.translateYProperty(), random() * 600)
      )
      );
    }
    generateButtonLayout(); //generates button layout after the animation has been added
    root.setBottom(userElements);

  }

  /**\
   * @param args
   *
   * This function calls the launch constructor for application which runs the start function. The function then creates the GUI and runs the
   * animation
   */
  public static void main(String[] args)
  {
    launch(args);
  }
}
