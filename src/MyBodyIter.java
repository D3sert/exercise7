import java.util.NoSuchElementException;

public class MyBodyIter implements BodyIterator {
    private MyCosmicComponentNode node;
    private BodyIterator iter;

    public MyBodyIter(MyCosmicComponentNode node) {
        this.node = node;
    }

    @Override
    public boolean hasNext() {
        return node != null || (iter != null && iter.hasNext());
    }


    @Override
    public void remove() {

    }

    @Override
    public Body next() {
        if (node == null) {
            if (iter != null && !iter.hasNext()) {throw new java.util.NoSuchElementException();}
        }
        Body body;

        if (iter != null && iter.hasNext()) {
            return iter.next();
        } else {
            CosmicComponent c = node.getComponent();

            if (c.getClass() == Body.class) {
                body = (Body) c;
                node = node.next();
            } else if (c.getClass() == ComplexCosmicSystem.class) {
                iter = ((ComplexCosmicSystem) c).iterator();
                node = node.next();

                body = iter.next();
            } else return null;

            return body;
        }
    }
}