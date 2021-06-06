public class MyBodyCollectionView implements BodyCollection {

    @Override
    public boolean add(Body b) {
        return false;
    }

    @Override
    public boolean contains(Body b) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Body[] toArray() {
        return new Body[0];
    }

    @Override
    public BodyIterator iterator() {
        return null;
    }
}
