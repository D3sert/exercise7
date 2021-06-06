public class ComplexCosmicSystem implements CosmicComponent, BodyIterable, CosmicSystemIndex {

    private final String name;
    private MyCosmicComponentNode head;
    private MyCosmicComponentNode tail;

    // Initialises this system with a name and at least two cosmic components.
    // Precondition: c1, c2 and all entries in ci are not null
    public ComplexCosmicSystem(String name, CosmicComponent c1, CosmicComponent c2,
                               CosmicComponent... ci) {

        this.name = name;
        this.add(c1);
        this.add(c2);

        for (CosmicComponent c : ci) {
            this.add(c);
        }
    }

    public BodyCollection getBodies() {
        return null;
    }

    // Adds 'comp' to the list of cosmic components of the system if the list does not already contain a
    // component with the same name as 'comp', otherwise does not change the object state. The method
    // returns 'true' if the list was changed as a result of the call and 'false' otherwise.
    public boolean add(CosmicComponent comp) {
        if (comp == null) {
            return false;
        }

        if (head == null) {
            tail = head = new MyCosmicComponentNode(comp, null, null);
            return true;
        }

        MyCosmicComponentNode newTail = head.add(comp);
        if (newTail != null) {
            tail = newTail;
            return true;
        }
        return false;
    }

    // Returns the name of this system.
    public String getName() {
        return name;
    }

    // returns the number of CosmicComponent entries of the list.
    public int size() {
        if (head == null) {
            return 0;
        }
        return head.size();
    }

    //Returns the overall number of bodies (i.e. objects of type 'Body') contained in the ComplexCosmicSystem
    //For instance, the System "Solar System{Sun, Earth System{Earth, Moon}, Jupiter System{Jupiter}" contains 4 bodies (Sun, Earth, Moon and Jupiter).
    //
    //Constraint: use the concept of dynamic binding to fulfill this task, i.e. don't use type casts, getClass() or instanceOf()
    public int numberOfBodies() {
        MyCosmicComponentNode nextNode = head;
        int cnt = 0;
        while (nextNode != null) {
            cnt += nextNode.getComponent().numberOfBodies();
            nextNode = nextNode.next();
        }
        return cnt;
    }

    // Returns a readable representation with the name of the
    // system and all bodies in respective order of the list.
    public String toString() {
        String result = this.name + "{";

        MyCosmicComponentNode nextNode = head;
        while (nextNode != null) {
            result += nextNode.getComponent();
            nextNode = nextNode.next();
            result += nextNode == null ? "" : ", ";
        }

        result += "}";

        return result;
    }

    //Two objects of type `ComplexCosmicSystem` are equal, if they contain the same bodies (regardless of their order)
    // and the same subsystems.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexCosmicSystem that = (ComplexCosmicSystem) o;

        if (numberOfBodies() != that.numberOfBodies()) return false;

        MyCosmicComponentNode curr = head;
        while (curr != null) {
            if (!that.contains(curr.getComponent())) {
                return false;
            }
            curr = curr.next();
        }

        return true;
    }

    private boolean contains(CosmicComponent c) {
        MyCosmicComponentNode curr = head;
        while (curr != null) {
            if (c.equals(curr.getComponent())) {
                return true;
            }
            curr = curr.next();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 0;

        MyCosmicComponentNode curr = head;
        while (curr != null) {
            hash += curr.getComponent().hashCode();
            curr = curr.next();
        }

        return hash;
    }

    public double getMass() {
        MyCosmicComponentNode nextNode = head;
        double mass = 0;
        while (nextNode != null) {
            mass += nextNode.getComponent().getMass();
            nextNode = nextNode.next();
        }
        return mass;
    }

    public Vector3 getMassCenter() {
        MyCosmicComponentNode nextNode = head;
        Vector3 v = new Vector3(0, 0, 0);
        while (nextNode != null) {
            v = v.plus(nextNode.getComponent().getMassCenter().times(nextNode.getComponent().getMass()));
            nextNode = nextNode.next();
        }
        if (this.getMass() != 0) {
            v = v.times((1 / this.getMass()));
        }
        return v;
    }

    // Returns an iterator over elements of type 'Body'.
    @Override
    public BodyIterator iterator() {
        return new MyBodyIter(head);
    }

    // Returns the 'ComplexCosmicSystem' with which a body is
    // associated. If 'b' is not contained as a key, 'null'
    // is returned.
    @Override
    public ComplexCosmicSystem getParent(Body b) {
        if (!contains(b)) return null;

        MyCosmicComponentNode current = head;
        CosmicComponent currentComponent;

        while (current != null) {
            currentComponent = current.getComponent();
            if (currentComponent.numberOfBodies() == 1) { //component is of type 'Body'
                if (currentComponent.equals(b)) {
                    return this;
                }
            } else if (currentComponent.numberOfBodies() > 1) { //component is of type 'ComplexCosmicsystem'
                CosmicSystemIndex csi = (CosmicSystemIndex) currentComponent;

                if (csi.contains(b)) {
                    return csi.getParent(b);
                }
            } else return null;

            current = current.next();
        }
        return null;
    }

    // Returns 'true' if the specified 'b' is listed
    // in the index.
    @Override
    public boolean contains(Body b) {
        BodyIterator iter = iterator();
        Body curr;

        while (iter.hasNext()) {
            curr = iter.next();
            if (curr.equals(b)) {
                return true;
            }
        }

        return false;
    }


}

class MyCosmicComponentNode {
    private CosmicComponent c;
    private MyCosmicComponentNode next;
    private MyCosmicComponentNode prev;

    MyCosmicComponentNode(CosmicComponent c, MyCosmicComponentNode next, MyCosmicComponentNode prev) {
        this.c = c;
        this.next = next;
        this.prev = prev;
    }

    public CosmicComponent getComponent() {
        return c;
    }

    public MyCosmicComponentNode next() {
        return next;
    }

    public MyCosmicComponentNode prev() {
        return prev;
    }

    public void setNext(MyCosmicComponentNode next) {
        this.next = next;
    }

    public void setPrev(MyCosmicComponentNode prev) {
        this.prev = prev;
    }

    MyCosmicComponentNode add(CosmicComponent c) {
        if (c.getName().equals(this.c.getName())) {
            return null;
        }

        if (next == null) {
            next = new MyCosmicComponentNode(c, null, this);
            return next;
        } else {
            return next.add(c);
        }

    }

    // Precondition: 'i' is a valid index.
    boolean add(int i, CosmicComponent c) {
        if (i == 0) {
            this.prev = this.prev.next = new MyCosmicComponentNode(c, this, this.prev);
            return true;
        } else {
            if (next != null) {
                return next.add(i - 1, c);
            }
            if (i == 1) {
                return add(c) != null;
            }
        }
        return false;

    }

    boolean remove(CosmicComponent c) {
        MyCosmicComponentNode removeNode = this.next;
        while (removeNode != null && !removeNode.c.getName().equals(c.getName())) {
            removeNode = removeNode.next;
        }
        if (removeNode != null) {
            //inner node
            removeNode.prev.next = removeNode.next;
            removeNode.next.prev = removeNode.prev;
            return true;
        }
        return false;
    }

    MyCosmicComponentNode removeHead() {
        if (this.next != null) {
            this.next.prev = null;
        }
        return this.next;
    }

    MyCosmicComponentNode removeTail() {
        if (this.prev != null) {
            this.prev.next = null;
        }
        return this.prev;
    }

    CosmicComponent get(String name) {
        if (c.getName().equals(name)) {
            return c;
        }

        if (next == null) {
            return null;
        }
        return next.get(name);

    }

    int size() {
        if (next == null) {
            return 1;
        }
        return 1 + next.size();
    }

    public String toString() {
        return next == null ? this.c + "\n\t" : this.c + "\n\t" + next.toString();

    }
}