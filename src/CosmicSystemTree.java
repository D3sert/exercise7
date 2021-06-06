import java.awt.*;


//This class represents a binary search tree for objects of class 'CosmicSystem'
public class CosmicSystemTree {

    //DONE: Define variables.
    private TANode root;
    private int counter;

    // Adds a system of bodies to the tree. Since the keys of the tree are the names of bodies,
    // adding a system adds multiple (key, value) pairs to the tree, one for each body of the
    // system, with the same value, i.e., reference to the cosmic system.
    // An attempt to add a system with a body that already exists in the tree
    // leaves the tree unchanged and the returned value would be 'false'.
    // For example, it is not allowed to have a system with the bodies "Earth" and "Moon" and another
    // system with the bodies "Jupiter" and "Moon" indexed by the tree, since "Moon" is not unique.
    // The method returns 'true' if the tree was changed as a result of the call and
    // 'false' otherwise.
    public boolean add(CosmicSystem system) {
        //DONE: implement method
        for (int i = 0; i < system.size(); i++) {
            String sysname = system.get(i).getName();
            if (!this.myput(sysname, system)) {
                return false;
            }
        }
        return true;
    }

    public boolean myput(String k, CosmicSystem v) {
        if (root == null) {
            root = new TANode(k, v);
            counter++;
            return true;
        } else if (this.root.find(k) != null) {
            return false;
        } else {
            root.put(k, v);
            counter++;
            return true;
        }
    }


    // Returns the cosmic system in which a body with the specified name exists.
    // For example, if the specified name is "Europa", the system of Jupiter (Jupiter, Io,
    // Europa, Ganymed, Kallisto) will be returned.
    // If no such system is found, 'null' is returned.
    public CosmicSystem get(String k) {
        //DONE: implement method
        if (root == null) {
            return null;
        }
        TANode node = root.find(k);
        return node == null ? null : node.value();

    }


    // Returns the overall number of bodies indexed by the tree.
    public int numberOfBodies() {
        //DONE: implement method
        return counter;
    }

    // Returns a readable representation with (key, value) pairs, sorted alphabetically by the key.
    //E.g.,
    //    (Deimos,Mars System)
    //    (Earth,Earth System)
    //
    //Hint: for this you will also need a method in CosmicSystem.java to access the name of a CosmicSystem object.
    public String toString() {
        return Stringmaker(root);

    }



    //BONUS TASK: sets a new canvas and draws the tree using StdDraw
    public void drawTree() {
        double x = .5;
        double y = .8;

        StdDraw.setCanvasSize(1000 , 1000);
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
        mydraw(root, x, y);


        //DONE: implement method (optional bonus task)

    }

    public void mydraw(TANode node, double x, double y) {
        Font font1 = new Font("Comic Sans", Font.BOLD, 15);
        Font font2 = new Font("Comic Sans", Font.BOLD, 10);
        if (node != null) {
            if (node.left != null) {
                StdDraw.line(x,y,x-0.08,y-0.08);
                mydraw(node.left, x - 0.08, y - 0.08);
            }

            System.out.println("Drawing");
            StdDraw.circle(x, y, 0.03);
            StdDraw.setFont(font1);

            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.text(x, y+0.005, node.key);
            StdDraw.setFont(font2);

            StdDraw.setPenColor(StdDraw.DARK_GRAY);
            StdDraw.text(x, y - 0.005, node.value.getName());

            StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);


            if (node.right != null) {
                StdDraw.line(x,y,x+0.08,y-0.08);
                mydraw(node.right, x + 0.08, y - 0.08);
            }
        }
    }


    //DONE: Define additional class(es) implementing the binary tree (either here or outside class).

    public String Stringmaker(TANode node) {
        String s = "";
        if (node != null) {
            if (node.left != null) {
               s += Stringmaker(node.left);
            }
            s += "(" + node.key + "," + node.value().getName() + ")\n";
            if (node.right != null) {
                s += Stringmaker(node.right);
            }
        }
        return s;
    }


    private class TANode {
        private String key;
        private CosmicSystem value;
        private TANode left, right;

        private TANode(String k, CosmicSystem v) {
            key = k;
            value = v;
        }

        private int compare(String k) {
            if (k == null) {
                return key == null ? 0 : -1;
            }
            if (key == null) {
                return 1;
            }
            return k.compareTo(key);
        }

        private CosmicSystem put(String k, CosmicSystem v) {
            int cmp = compare(k);
            if (cmp < 0) {
                if (left != null) {
                    return left.put(k, v);
                }
                left = new TANode(k, v);
            } else if (cmp > 0) {
                if (right != null) {
                    return right.put(k, v);
                }
                right = new TANode(k, v);
            } else {
                CosmicSystem result = value;
                value = v;
                return result;
            }
            return null;
        }

        private TANode find(String k) {
            int cmp = compare(k);
            if (cmp == 0) {
                return this;
            }
            TANode node = cmp < 0 ? left : right;
            if (node == null) {
                return null;
            }
            return node.find(k);
        }

        private CosmicSystem value() {
            return value;
        }
    }


}