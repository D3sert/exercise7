
//This class represents a linked list for objects of class 'Body'
public class CosmicSystem {

    //TODO: Define variables.
    private int counter;
    private String name;
    private DLNode head;
    private DLNode tail;


    // Initialises this system as an empty system with a name.
    public CosmicSystem(String name) {
        //TODO: implement constructor.
        this.name = name;
        this.counter = 0;
    }


    // Adds 'body' to the end of the list of bodies if the list does not already contain a
    // body with the same name as 'body', otherwise does not change the object state. The method
    // returns 'true' if the list was changed as a result of the call and 'false' otherwise.



    //Test3
    public void deleteEveryNthElement(int N){
        if (N <= 0)
            return;

        int i = 0;
        int size = size();
        DLNode current = head;
        while(current != null) {
            if (i % N == 0){
                if(i == 0) {
                    head = head.next;
                    if(head != null) {
                        head.prev = null;
                    }
                }
                else if(current.value.getName().equals(tail.value.getName())){
                    tail = tail.prev;
                    if(tail != null) {
                        tail.next = null;
                    }
                }
                else {
                    if(current.prev != null) {
                        current.prev.next = current.next;
                    }
                    if(current.next != null) {
                        current.next.prev = current.prev;
                    }
                }
            }
            i++;
            current = current.next;
        }
    }


    //ADD LAST
    public boolean add(Body body) {
        if (find(body) == -1) {
            DLNode temp = new DLNode(body);
            temp.next = null;
            temp.prev = tail;
            if (tail != null) {
                tail.next = temp;
            }
            tail = temp;
            if (head == null) {
                head = temp;
            }
            counter++;
            return true;
        }
        return false;
    }

    // Returns the 'body' with the index 'i'. The body that was first added to the list has the
    // index 0, the body that was most recently added to the list has the largest index (size()-1).
    // Precondition: 'i' is a valid index.
    public Body get(int index) {
        //TODO: implement method.
        if (index < 0 || index >= counter) {
            return null;
        } else {
            DLNode curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.next;
            }
            return curr.value;
        }
    }

    // Returns the body with the specified name or 'null' if no such body exits in the list.
    public Body get(String v) {
        //TODO: implement method.
        int i = 0;
        DLNode n = head;
        while (n != null && !(v == null ? v == n.value().getName() : v.equals(n.value().getName()))) {
            i++;
            n = n.next();
        }
        return n == null ? null : n.value();
    }

    // Returns the body with the same name as the input body or 'null' if no such body exits in the list.
    public Body get(Body body) {
        //TODO: implement method.
        int i = 0;
        DLNode n = head;
        String name = body.getName();
        while (n != null && !(body == null ? name == n.value().getName() : name.equals(n.value().getName()))) {
            i++;
            n = n.next();
        }
        return n == null ? null : n.value();
    }

    // returns the number of entries of the list.
    public int size() {
        return counter;
    }

    public String getName() {
        return this.name;
    }

    //TODO:START OF NEW METHODS

    // Inserts the specified 'body' at the specified position
    // in this list if 'i' is a valid index and there is no body
    // in the list with the same name as that of 'body'.
    // Shifts the element currently at that position (if any) and
    // any subsequent elements to the right (adds 1 to their
    // indices). The first element of the list has the index 0.
    // Returns 'true' if the list was changed as a result of
    // the call, 'false' otherwise.

    //ADDBEFOREatINDEX
    public boolean add(int index, Body body) {
        //TODO: implement method.
        DLNode temp = new DLNode(body);
        if (index < 0 || index > size() || find(body) != -1) {
            return false;
        }

        //ADDBEG
        else if (index == 0) {
            temp.next = head;
            temp.prev = null;
            if (head != null) {
                head.prev = temp;
            }
            head = temp;
            if (tail == null) {
                tail = temp;
            }
            counter++;
            return true;
        }
        //ADDLAST
        else if (index == size()) {
            temp.next = null;
            temp.prev = tail;
            if (tail != null) {
                tail.next = temp;
            }
            tail = temp;
            if (head == null) {
                head = temp;
            }
            counter++;
            return true;
        }
        //ADD[I]
        else {
            DLNode curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.next;
            }
            DLNode prev = curr.prev;
            prev.next = temp;
            temp.prev = prev;
            temp.next = curr;
            curr.prev = temp;
            counter++;
            return true;
        }
    }

    //removes the body at index i from the list, if i is a valid index
    //returns true if removal was done, and false otherwise (invalid index)
    public boolean remove(int index) {
        DLNode curr = head;
        if (head == null || curr == null || index > size() - 1) {
            return false;
        }


        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }

        if (head == curr) {
            head = curr.next;
        }

        if (curr.next != null) {
            curr.next.prev = curr.prev;
        }

        if (curr.prev != null) {
            curr.prev.next = curr.next;
        }

        curr = null;

        //TODO: implement method.
        counter--;
        return true;
    }

    //removes a body from the list, if the list contains a body with the same name as the input body
    //returns true if removal was done, and false otherwise (no body with the same name)
    public boolean remove(Body body) {
        //TODO: implement method.
        int index = find(body);
        if (index >= 0) {
            remove(index);
            return true;
        }
        return false;
    }

    // Returns a new list that contains the same elements as this list in reverse order. The list 'this'
    // is not changed and only the references to the bodies are copied, not their content (shallow copy).
    public CosmicSystem reverse() {
        CosmicSystem Reverse = new CosmicSystem(this.name);
        DLNode curr = tail;
        while (curr != null) {
            Reverse.add(curr.value);
            curr = curr.prev;
        }
        //TODO: implement method.
        return Reverse;
    }

    // Returns a readable representation with the name of the system and all bodies in order of the list.
    // E.g.,
    // Jupiter System:
    // Jupiter, 1.898E27 kg, radius: 6.9911E7 m, position: [0.0,0.0,0.0] m, movement: [0.0,0.0,0.0] m/s.
    // Io, 8.9E22 kg, radius: 1822000.0 m, position: [0.0,0.0,0.0] m, movement: [0.0,0.0,0.0] m/s.
    //
    //Hint: also use toString() in Body.java for this.
    public String toString() {
        String s = this.name;
        DLNode curr = head;
        while (curr != null) {
            s += "\n" + curr.value.toString();
            curr = curr.next;
        }
        //TODO: implement method.
        return s;
    }

    //DONE: Define additional class(es) implementing the linked list (either here or outside class).

    //find body return index or -1
    public int find(Body body) {
        int index = 0;
        DLNode curr = head;
        if (get(body) == null) {
            return -1;
        }

        for (int i = 0; i < size() - 1; i++) {
            if (curr.value == body) {
                return index;
            }
            if (curr.value.getName() == body.getName()) {
                return index;
            }
            curr = curr.next;
            index++;

        }
        return -1;
    }

    public class DLNode {
        private Body value;
        private DLNode prev;
        private DLNode next;

        public DLNode(Body v) {
            value = v;
            DLNode prev;
            DLNode next;
        }

        public DLNode(Body v, DLNode p, DLNode n) {
            value = v;
            prev = p;
            next = n;
        }

        public Body value() {
            return value;
        }

        public DLNode next() {
            return next;
        }

        public DLNode prev() {
            return prev;
        }

        public void setValue(Body v) {
            value = v;
        }

        public DLNode remove() {
            next.prev = prev;
            prev.next = next;
            return this;
        }
    }
}
