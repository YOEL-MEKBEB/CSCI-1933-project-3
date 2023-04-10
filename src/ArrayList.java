public class ArrayList<T extends Comparable<T>> implements List<T>{
    private T[] array;

    private int size;
    private boolean isSorted = true;

    //initializes array with size of 2
    public ArrayList(){
        array = (T[]) new Comparable[2];
    }
    @Override
    //adds element at the end of the list
    public boolean add(T element) {
        //doesn't add if element is null
        if (element == null){
            return false;
        } else{
            if (isEmpty()){
                array[0] = element;
                size++;
            } else if (size() == array.length) {
                // calls resizeHelper() to resize the array length
                resizeHelper();
                array[size()]=element;
                size++;
            }else {
                array[size()] = element;
                size++;
            }
            //if size is greater than 1 compares the added element and the element before it to check if it's sorted
            if(size() == 1){
                isSorted = true;
            }else if(array[size()-1].compareTo(array[size()-2])<0){
                isSorted = false;
            }
            return true;
        }


    }
    // resizes the array list by 2 times
    public void resizeHelper(){
        T[] anotherArray = (T[])new Comparable[size()*2];
        for(int i = 0; i<size(); i++){
            anotherArray[i] = array[i];
        }
        array = anotherArray;
    }

    @Override
    // adds element at the specified index and moves all the elements after it to the right
    //resizes array if necessary
    public boolean add(int index, T element) {
        if(index < 0 || index >= array.length){
            return false;
        } else {
            if(size == array.length){
                this.resizeHelper();
            }
            for (int i = size()-1; i >= index; i--){
                array[i+1] = array[i];
            }
            array[index] = element;
            size++;

            if(size() == 1){
                isSorted = true;
            }else {
                //checks the case where the element is added at the beginning of the list
                if (index == 0) {
                    if (array[index].compareTo(array[index + 1]) > 0) {
                        isSorted = false;
                    }
                }
                //checks the case if the element is added at the end of the list
                else if (index == size() - 1) {
                    if (array[index].compareTo(array[index - 1]) < 0) {
                        isSorted = false;
                    }
                }
                //checks the case where the element is added between the beginning and the end of the list
                else if (array[index].compareTo(array[index + 1]) > 0 || array[index].compareTo(array[index - 1]) < 0) {
                    isSorted = false;
                }
            }
            return true;
        }
    }
    @Override
    //sets all the elements in the list to null and the size to 0
    public void clear() {
        final int num = size;
        for(int i = 0; i< num; i++){
            array[i] = null;
            size--;
        }
        isSorted = true;
        T[] originalArray = (T[]) new Comparable[2];
    }

    @Override
    //returns the element at the given index as long as the index is within bounds
    public T get(int index) {
        if(index>=0 && index< array.length) {
            return array[index];
        }else {
            return null;
        }
    }

    @Override
    // returns the first index of the given element
    public int indexOf(T element) {
        if(element==null){
            return -1;
        }
        for(int i = 0 ; i< array.length; i++){
            if(array[i] == element){
                return i;
            }
        }
        return -1;
    }

    @Override
    //returns true if size is 0, otherwise returns false
    public boolean isEmpty() {
        if (size() == 0){
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
        for(int i = 1; i<size; i++){
            T currentValue = array[i];
            int j = i -1;
            while(j>=0 && array[j].compareTo(currentValue) > 0){
                array[j+1] = array[j];
                j--;
            }
            array[j+1] = currentValue;
        }
        isSorted = true;
    }
    @Override
    //removes an element at the given index and returns it
    public T remove(int index) {
        if(size == 0){
            return null;
        }
        else if(index<0 || index>=size()){
            return null;
        }else{
            T value = array[index];
            array[index] = null;
                while (index < size) {
                    array[index] = array[index+1];
                    index++;
                }
            size--;
                if(size() == 0){
                    isSorted = true;
                }
                else {
                    int i = 0;
                        isSorted = true;
                    while (i < index-2) {
                        // breaks out of loop if a case where it's not sorted is found because anything after it won't
                        //change the status
                        if(array[i].compareTo(array[i+1])>0) {
                            isSorted = false;
                            break;
                        }
                        i++;
                    }

                }

            return value;
        }
    }

    @Override
    //calls remove() to remove all the elements that are not equal to element
    public void equalTo(T element) {
        if(element!=null){
            int i = 0;
            while(i<size()){
                // "i" is decremented in the if statement to account for the size decreasing.
                if(array[i] != element){
                    remove(i);
                    i--;
                }
                i++;
            }
            isSorted = true;
        }
    }

    @Override
    // reverses the list and checks if it's sorted
    public void reverse() {
        isSorted = true;
        for(int i = 0; i<size/2; i++){
            T currentValue = array[i];
            array[i] = array[size - i-1];
            array[size-i-1] = currentValue;

        }
        int i = 0;
        while (i < size-1) {
            if(array[i].compareTo(array[i+1])>0) {
                isSorted = false;
                break;
            }
            i++;
        }
    }

    @Override
    // sorts the given list and the current list and merges them
    // together by comparing and adding the smallest value first
    public void merge(List<T> otherList) {
        if(otherList!=null){
            this.sort();
            this.isSorted = true;
            ArrayList<T> other = (ArrayList<T>)otherList;
            other.sort();
            T[] mergedArray = (T[])new Comparable[this.size+other.size];
            int index = 0;
            int i = 0;
            int j = 0;
            mergedArray[index] = this.get(i);
            while(i<this.size && j<other.size){
                if(this.get(i).compareTo(other.get(j))<0){
                    mergedArray[index] = this.get(i);
                    index++;
                    i++;
                }else {
                    mergedArray[index] = other.get(j);
                    index++;
                    j++;
                }

            }
            //adds the remaining of this list if other list is empty
            while(i<this.size){
                mergedArray[index] = this.get(i);
                index++;
                i++;
            }
            // adds the remaining of other list if this list is empty
            while(j<other.size){
                mergedArray[index] = other.get(j);
                index++;
                j++;
            }
            array = mergedArray;
            size = mergedArray.length;
            isSorted = true;
        }

    }
    @Override
    // rotates the list by n number of times and checks if it's sorted
    public boolean rotate(int n) {
        if(n<=0 || this.size()<=1) {
            return false;
        }
        else{
            T currentValue = null;
            for(int j = 0; j<n; j++) {
                currentValue = array[size-1];
                for (int i = size()-1; i >0; i--) {
                    array[i] = array[i-1];
                }
                array[0] = currentValue;
            }

            int i = 0;
            while (i < size-1) {
                isSorted = true;
                if(array[i].compareTo(array[i+1])>0) {
                    isSorted = false;
                    break;
                }
                i++;
            }

            return true;
        }

    }


    public String toString(){
        String result = "";
        for(int i = 0; i < size(); i++){
            result += (String) array[i];
            result += "\n";
        }
        return result;
    }
    @Override
    //returns whether the list is sorted or not.
    public boolean isSorted() {
        return isSorted;
    }
}
