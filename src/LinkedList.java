public class LinkedList<T extends Comparable<T>> implements List<T> {

    private Node<T> firstNode;
    private Node<T> pointerNode;
    private int size = 0;
    private boolean isSorted = true;

    //initializes the firstNode as null
    public LinkedList(){
        firstNode = new Node<T>(null);
        isEmpty();
    }
    @Override
    //adds element at the end of the list
    //if the list is empty, assigns the data to firstNode
    public boolean add(T element) {
        if(element == null){
            return false;
        }else {
            if(isEmpty()){
                firstNode = new Node<>(element);

            }else if(size == 1){
                Node<T> newNode = new Node<T>(element);
                firstNode.setNext(newNode);
                pointerNode = firstNode.getNext();
            }else {
                Node<T> newNode = new Node<T>(element);
                pointerNode = firstNode.getNext();
                while (pointerNode.getNext()!=null){
                    pointerNode = pointerNode.getNext();
                }
                pointerNode.setNext(newNode);
                pointerNode = pointerNode.getNext();

            }
            size++;
            if(size() == 1){
                isSorted = true;
            }else if(size() == 2) {
                if(firstNode.getData().compareTo(firstNode.getNext().getData())>0){
                    isSorted = false;
                }
            }
            else {
                pointerNode = firstNode;
                for(int i = 0; i < size()-2; i++) {
                    pointerNode = pointerNode.getNext();
                    if(pointerNode.getData().compareTo(pointerNode.getNext().getData())>0) {
                        isSorted = false;
                    }
                }
            }

            return true;
        }
    }
    @Override
    //adds element at specified index
    public boolean add(int index, T element) {
        if(element == null || index<0|| index > size()){
            return false;
        } else if (size() == 0 && index == 0) {
            firstNode.setData(element);
            isSorted = true;
            size++;
            return true;
        } else if (index == 0) {
            Node<T> newNode = new Node<>(element, firstNode);
            firstNode = newNode;
            size++;
            isSortHelper();
            return true;
        } else {
            pointerNode = firstNode;
            for(int i = 0; i <index-1; i++){
                pointerNode = pointerNode.getNext();
            }

            Node<T> newNode = new Node<T>(element, pointerNode.getNext());
            pointerNode.setNext(newNode);

            size++;
            isSortHelper();
            return true;
        }
    }
    //helper to check if list isSorted
    public void isSortHelper(){
        if(size() == 1){
            isSorted = true;
        }else if(size() == 2) {
            if(firstNode.getData().compareTo(firstNode.getNext().getData())>0){
                isSorted = false;
            }
        } else {
            pointerNode = firstNode;
            if(true) {
                for(int i = 0; i < size()-2; i++) {
                    if (pointerNode.getData().compareTo(pointerNode.getNext().getData()) > 0) {
                        isSorted = false;
                    }
                    pointerNode = pointerNode.getNext();

                }
            }
        }
    }
    @Override
    //sets all the elements in the list to null and the size to 0
    public void clear() {
        if(firstNode!= null) {
            firstNode =new Node<>(null);
            size = 0;
            isSorted = true;
        }
    }

    @Override
    //returns the element at the given index as long as the index is within bounds
    public T get(int index) {
        if(index == size||index<0){
            return null;
        }else {
            pointerNode = firstNode;
            for (int i = 0; i < index; i++) {
                if(pointerNode.getNext()!=null) {
                    pointerNode = pointerNode.getNext();
                }else {
                    return null;
                }
            }
            return pointerNode.getData();
        }
    }

    @Override
    // returns the first index of the given element
    // A linked list doesn't have an index, so the method returns an abstract index
    public int indexOf(T element) {
        if(element == null){
            return -1;
        }else {
            pointerNode = firstNode;

            for (int i = 0; i < size(); i++) {
                if(pointerNode.getData() == element){
                    return i;
                }else {
                    pointerNode = pointerNode.getNext();
                }
            }
            return -1;
        }
    }

    @Override
    //returns true if size is 0, otherwise returns false
    public boolean isEmpty() {
       if(size()==0){
           return true;
       }else {
           return false;
       }
    }

    @Override
    //returns size
    public int size() {
        return size;
    }

    @Override
    //sorts the list using insertion sort
    public void sort() {
        Node<T> header = new Node<T>(null,firstNode);
        for(int i = 1; i<size(); i++){
            pointerNode = header;
            for(int k = 0; k<size()-i-1; k++){
                pointerNode = pointerNode.getNext();
            }
            // "j" is set to a max value of size-3 to make use of getNext()

            int j = size() - i-2;

            while(j<size()-2 && pointerNode.getNext().getData().compareTo(pointerNode.getNext().getNext().getData())>0){
                T data1 = pointerNode.getNext().getData();
                T data2 = pointerNode.getNext().getNext().getData();
                pointerNode.getNext().setData(data2);
                pointerNode.getNext().getNext().setData(data1);
                pointerNode = pointerNode.getNext();
                j++;
            }

        }
        isSorted = true;
    }

    @Override
    //removes the element at the given index
    public T remove(int index) {
        pointerNode = firstNode;
        Node<T> removedNode;
        //outElement is used instead of return to allow the sort checking to run
        T outElement = null;
        if(size == 0){
            outElement = null;
        } else if(index<0 || index>=size()){
            outElement = null;
        } else if (index == 0) {
            removedNode = firstNode;
            firstNode = firstNode.getNext();
            size--;
            outElement = removedNode.getData();
        } else {
            for (int i = 0; i < index-1; i++) {
                pointerNode = pointerNode.getNext();
            }
            if(index==size()-1){
                removedNode = pointerNode.getNext();
                pointerNode.setNext(null);
                size--;
            }else {
                removedNode = pointerNode.getNext();
                pointerNode.setNext(pointerNode.getNext().getNext());
                size--;
            }
            outElement = removedNode.getData();
        }

        if(size() == 0 || size() == 1){
            isSorted = true;
        }
        else {
            int i = 0;
            isSorted = true;
            pointerNode = firstNode;
            while (pointerNode.getNext()!=null) {
                if (pointerNode.getData().compareTo(pointerNode.getNext().getData()) > 0) {
                    isSorted = false;
                }
                pointerNode = pointerNode.getNext();
            }
        }
        return outElement;
    }

    @Override
    //sets all the elements that are not equal to element to null and changes isSorted to true;
    public void equalTo(T element) {

        pointerNode= firstNode;
        Node<T> removedNode = firstNode;
        if (element != null) {
            int i = 0;
            while(i<size()){

                if (!pointerNode.getData().equals(element)) {
                    if(i == 0){
                        pointerNode = pointerNode.getNext();
                        removedNode.setNext(null);
                        removedNode = pointerNode;
                        firstNode = pointerNode;
                    }else {
                        pointerNode = pointerNode.getNext();
                        removedNode.setNext(pointerNode);
                    }
                    size--;
                    i--;
                } else {
                    pointerNode = pointerNode.getNext();
                   if(removedNode.getNext()!=null && !removedNode.getNext().equals(pointerNode)){
                       removedNode = removedNode.getNext();
                   }

                }
                i++;
            }
        }
        isSorted = true;

    }
    @Override
    //reverses the list and checks if it's sorted
    public void reverse() {
        Node<T> header = new Node<T>(null,firstNode);
        Node<T> addPointer = header;
        for(int i = 0; i < size(); i++){
            pointerNode =  header;
            for(int j = 0; j<size(); j++) {
                pointerNode = pointerNode.getNext();

            }
            pointerNode.setNext(firstNode);
            addPointer.setNext(pointerNode);
            addPointer = addPointer.getNext();
        }
        addPointer.setNext(null);
        firstNode = header.getNext();

        if(size() <= 1){
            isSorted = true;
        }else if(size() == 2) {
            isSorted = true;
            if(firstNode.getData().compareTo(firstNode.getNext().getData())>0){
                isSorted = false;
            }
        }
        else {

            isSorted = true;
            pointerNode = firstNode;
            for(int i = 0; i < size()-1; i++) {
                if(pointerNode.getData().compareTo(pointerNode.getNext().getData())>0) {
                    isSorted = false;
                    break;
                }
                pointerNode = pointerNode.getNext();

            }
        }
    }

    @Override
    // sorts the given list and the current list and merges them
    // together by comparing and adding the smallest value first
    public void merge(List<T> otherList) {
        if(otherList!=null){
            this.sort();
            this.isSorted = true;
            LinkedList<T> other = (LinkedList<T>) otherList;
            other.sort();
            Node<T> newHeaderNode = new Node<T>(null);
            Node<T> newPointer = newHeaderNode;
            Node<T> thisPointer = this.firstNode;
            Node<T> otherPointer = other.firstNode;

            for(int i = 0; i < this.size()+other.size(); i++){
                if(this.isEmpty()||thisPointer == null){
                    newPointer.setNext(otherPointer);
                    newPointer = newPointer.getNext();
                    otherPointer = otherPointer.getNext();
                }else if(other.isEmpty()|| otherPointer == null){
                    newPointer.setNext(thisPointer);
                    newPointer = newPointer.getNext();
                    thisPointer = thisPointer.getNext();
                }else {
                    if(thisPointer.getData().compareTo(otherPointer.getData())<0){
                        newPointer.setNext(thisPointer);
                        newPointer = newPointer.getNext();
                        thisPointer = thisPointer.getNext();
                    }else {
                        newPointer.setNext(otherPointer);
                        newPointer = newPointer.getNext();
                        otherPointer = otherPointer.getNext();
                    }
                }
            }
            size = this.size()+other.size();
            isSorted = true;
        }

    }

    @Override
    // rotates the list by n number of times and checks if it's sorted
    public boolean rotate(int n) {
        if(n<=0 || size()<=1){
            return false;
        }else {
            pointerNode = firstNode;
            for(int i = 0; i < size()-1; i++){
                pointerNode = pointerNode.getNext();
            }
            pointerNode.setNext(firstNode);
            pointerNode = firstNode;
            for(int i = 0; i<size-n-1; i++){
                pointerNode = pointerNode.getNext();
            }
            firstNode = pointerNode.getNext();
            pointerNode.setNext(null);

            if(size() == 1){
                isSorted = true;
            }else if(size() == 2) {
                if(firstNode.getData().compareTo(firstNode.getNext().getData())>0){
                    isSorted = false;
                }
            }
            else {
                isSorted = true;
                pointerNode = firstNode;
                for(int i = 0; i < size()-2; i++) {
                    pointerNode = pointerNode.getNext();
                    if(pointerNode.getData().compareTo(pointerNode.getNext().getData())>0) {
                        isSorted = false;
                    }
                }
            }

            return true;
        }
    }

    @Override
    //returns whether the list is sorted or not.
    public boolean isSorted() {
        return isSorted;
    }

    public String toString(){

        pointerNode = firstNode;
        String result = (String) pointerNode.getData() + "\n";
        for(int i = 0; i <size()-1; i++){
            pointerNode = pointerNode.getNext();
            result+=(String) pointerNode.getData() + "\n";

        }
        return result;
    }
}
