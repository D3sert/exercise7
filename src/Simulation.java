import java.util.Vector;

public class Simulation {

    // gravitational constant
    public static final double G = 6.6743e-11;

    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9;

    // all quantities are based on units of kilogram respectively second and meter.

    // The main simulation method using instances of other classes.
    public static void main(String[] args) {

        //Done: change implementation of this method according to 'Aufgabe1.md'.

        Body sun = new Body("Sol",1.989e30,696340e3,new Vector3(0,0,0),new Vector3(0,0,0),StdDraw.YELLOW);
        Body earth = new Body("Earth",5.972e24,6371e3,new Vector3(-1.394555e11,5.103346e10,0),new Vector3(-10308.53,-28169.38,0),StdDraw.BLUE);
        Body mercury = new Body("Mercury",3.301e23,2440e3,new Vector3(-5.439054e10,9.394878e9,0),new Vector3(-17117.83,-46297.48,-1925.57),StdDraw.GRAY);
        Body venus = new Body("Venus",4.86747e24,6052e3,new Vector3(-1.707667e10,1.066132e11,2.450232e9),new Vector3(-34446.02,-5567.47,2181.10),StdDraw.PINK);
        Body mars = new Body("Mars",6.41712e23,3390e3,new Vector3(-1.010178e11,-2.043939e11,-1.591727E9),new Vector3(20651.98,-10186.67,-2302.79),StdDraw.RED);

        CosmicSystem CSystem = new CosmicSystem("SOL");
        CSystem.add(sun);
        CSystem.add(earth);
        CSystem.add(mercury);
        CSystem.add(venus);
        CSystem.add(mars);


        StdDraw.setCanvasSize(500, 500);
        StdDraw.setXscale(-2*AU,2*AU);
        StdDraw.setYscale(-2*AU,2*AU);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.BLACK);

        double seconds = 0;


        // simulation loop
        while(true) {

            seconds++; // each iteration computes the movement of the celestial bdies within one second.

            // for each body (with index i): compute the total force exerted on it.
            //
            for (int i = 0;i < CSystem.size(); i++){
                Vector3 forceOnBody = new Vector3(0,0,0);
                Body curr = CSystem.get(i);
                for (int j = 0; j< CSystem.size();j++){
                    if(i==j) {continue;}
                    Vector3 forceToAdd = curr.gravitationalForce(CSystem.get(j));
                    forceOnBody = forceOnBody.plus(forceToAdd);
                }
                curr.setForce(forceOnBody);
            }

            for (int i = 0;i<CSystem.size();i++){
                CSystem.get(i).move();
            }

            // show all movements in StdDraw canvas only every 3 hours (to speed up the simulation)
            if (seconds%(3*3600) == 0) {
                // clear old positions (exclude the following line if you want to draw orbits).
                StdDraw.clear(StdDraw.BLACK);
                // draw new positions
                for (int i = 0; i < CSystem.size(); i++) {
                    CSystem.get(i).draw();
                }
                // show new positions
                StdDraw.show();
            }
            //System.out.println(earth.toString());

        }


    }

    //Done: remove static methods below.

}

//Done: answer additional questions of 'Aufgabe1'.

/*
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Was versteht man unter Datenkapselung? Geben Sie ein Beispiel, wo dieses Konzept in dieser Aufgabenstellung angewendet wird.
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Datenkapselung ist ein Prozess der bei OOP (Object Oriented Programming) zur Verwendung kommt. Die einer Klasse zugehörigen Attribute und Eigenschaften können, falls so als public definiert (von außen sichtbar), von Außenstehenden Methoden (durch getter und setter) gelesen bzw. geschrieben werden.

Datenkapselung ist der Prozess der dies, durch die Beschränkung des Zugriffes von außen, vermeiden soll. Objektvariablen die als Private klassifiziert sind können somit nur mehr über dafür vorgesehene Methoden verändert werden.

In der Aufgabenstellung wird dieses Konzept oft verwendet:

public class Vector3 {

    public double x;
    public double y;
    public double z;

Nehmen wir Vector3 als Beispiel:

    Zu beginn sind alle 3 Koordinaten (X,Y,Z) als public gesetzt. Somit kann jede andere Methode auf diese zugreifen und sie verändern.

    Zb verwenden wir in der Klasse Simulation:

    Body earth = new Body();
    earth.name = "Earth";
    earth.position = new Vector3();
    earth.position.x = 148e9; // minimal distance to sun in meters.
    earth.position.y = 0;
    earth.position.z = 0;

    wir greifen also somit von außen (einer anderen Klasse) auf eine objektvariable der Klasse Vector3 zu um die Koordinaten x y und z zu überschreiben.
    Falls wir die daten nun abkapseln möchten um die von außen gewährleisteten Zugriff zu unterbinden müssen wir die Objektvariablen auf private stellen und andere Methoden verwenden um diese variablen zu verändern.
    Erreichen können wir dies wie folgt:
    public class Vector3 {

        //DONE: change modifiers.
        private double x;
        private double y;
        private double z;

        Body earth = new Body(name,mass,radius,new Vector3(148e9,0,0),new Vector3(0,29.29e3,0),StdDraw.BLUE);

Da wir nun bei der Erstellung des Body Earth den Konstruktor dazu verwenden die Koordinaten zu setzen greifen wir nicht mehr von außen auf diese variablen zu, sondern über den Konstruktor, der zur innen Sicht der Klasse Vector3 gehört. Dies erlaubt uns nun den Körper so zu initialisieren wie gewünscht ohne dabei die Objektvariablen x, y und z für alles sichtbar zu machen. Somit  haben wir die daten des Vector3 von Simulation.java abgekapselt.

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Was versteht man unter Data Hiding? Geben Sie ein Beispiel, wo dieses Konzept in dieser Aufgabenstellung angewendet wird.
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Data Hidding ist der Prozess bei dem verhindert werden soll das alle variablen die eigentlich zur innen Sicht eines Prozesses gehören nach außen durchdringen. Dies wird durch die Verwendung von public und private Methoden erreicht.
Verwendet wird dies zb. bei der Initialisierung der Celestial Bodies:

        Body sun = new Body();
        sun.name = "Sol";
        sun.mass = 1.989e30; // kg
        sun.radius = 696340e3; // meters
        sun.position = new Vector3();
        sun.currentMovement = new Vector3();
        sun.position.x = 0; // meters
        sun.position.y = 0;
        sun.position.z = 0;

Der Körper Sun wird leer initialisiert und dann mit den gewünschten variablen befüllt. Für diese Vorgehensweise müssen die Objektvariablen des Körpers Sun public sein und gehörten dadurch zur Außensicht.

        Body sun = new Body("Sol",1.989e30,696340e3,new Vector3(),new Vector3(),StdDraw.YELLOW);

Um dies zu vermeiden können wir durch die Einführung eines Konstruktors den Körper nun so initialisieren das wir auf einzelne Attribute nicht mehr von außen zugreifen müssen. Dies erlaubt uns die objektvariablen auf private zu setzten und somit die von außen erlaubte Einsicht auf die variablen zu untersagen.

*/
