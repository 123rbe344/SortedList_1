import java.util.ArrayList;

/**
 * An ArrayList-like container of Strings that always keeps its elements
 * in ascending lexicographic order. New elements are placed using a
 * hand-written binary search (no Collections/Arrays.binarySearch).
 */
public class SortedList
{
    private final ArrayList<String> list = new ArrayList<>();

    /**
     * Binary search for target.
     * @return the index of target if found, otherwise -(insertionPoint + 1)
     *         (same convention as java.util.Arrays.binarySearch)
     */
    public int binarySearch(String target)
    {
        int low = 0;
        int high = list.size() - 1;
        while (low <= high)
        {
            int mid = low + (high - low) / 2;
            int cmp = list.get(mid).compareTo(target);
            if (cmp == 0)
            {
                return mid;
            }
            else if (cmp < 0)
            {
                low = mid + 1;
            }
            else
            {
                high = mid - 1;
            }
        }
        return -(low + 1);
    }

    /**
     * Adds element in its sorted position.
     * @return the index the element was inserted at
     */
    public int add(String element)
    {
        int idx = binarySearch(element);
        int insertAt = idx >= 0 ? idx : -(idx + 1);
        list.add(insertAt, element);
        return insertAt;
    }

    public String get(int index)
    {
        return list.get(index);
    }

    public int size()
    {
        return list.size();
    }

    public boolean isEmpty()
    {
        return list.isEmpty();
    }

    public void clear()
    {
        list.clear();
    }

    @Override
    public String toString()
    {
        return list.toString();
    }
}
