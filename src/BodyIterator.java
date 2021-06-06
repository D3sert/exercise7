// An iterator over elements of type 'Body'.
public interface BodyIterator extends java.util.Iterator<Body> {

    // Returns 'true' if the iteration has more elements.
    @Override
    boolean hasNext();
    // Returns the next element (i.e. body) in the iteration.
    Body next();

}