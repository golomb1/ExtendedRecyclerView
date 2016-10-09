package com.libs.golomb.extendedrecyclerview.Utils;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by tomer on 04/10/2016.
 * Observable list
 */

@SuppressWarnings("SuspiciousToArrayCall")
public class ObservableList<T> implements List<T> {

    private List<T> list;
    private List<ListObserver<T>> observers;

    public ObservableList() {
        this.list = new ArrayList<>();
        observers = new ArrayList<>();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @NonNull
    @Override
    public <T1> T1[] toArray(@NonNull T1[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(T t) {
        boolean result = list.add(t);
        for(ListObserver<T> observer : observers){
            observer.onItemAdded(t, result);
        }
        return result;
    }

    @Override
    public boolean remove(Object o) {
        boolean result = list.remove(o);
        for(ListObserver<T> observer : observers){
            observer.onItemRemoved(o, result);
        }
        return result;
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends T> c) {
        boolean result = list.addAll(c);
        for (ListObserver<T> observer : observers){
            observer.onCollectionAdded(c,result);
        }
        return result;
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends T> c) {
        boolean result = list.addAll(index, c);
        for (ListObserver<T> observer : observers){
            observer.onCollectionAdded(index, c,result);
        }
        return result;
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        boolean result = list.removeAll(c);
        for (ListObserver<T> observer : observers){
            observer.onCollectionRemoved(c,result);
        }
        return result;
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
        boolean result = list.retainAll(c);
        for (ListObserver<T> observer : observers){
            observer.onCollectionRetained(c,result);
        }
        return result;
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof ObservableList) {
            return list.equals(((ObservableList) o).list);
        }
        else{
            return list.equals(o);
        }
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public T set(int index, T element) {
        T item = list.set(index, element);
        for (ListObserver<T> observer : observers){
            observer.onItemSet(index,element,item);
        }
        return item;
    }

    @Override
    public void add(int index, T element) {
        list.add(index, element);
        for (ListObserver<T> observer : observers){
            observer.onItemAdded(index,element);
        }
    }

    @Override
    public T remove(int index) {
        T item = list.remove(index);
        for (ListObserver<T> observer : observers){
            observer.onItemRemoved(item);
        }
        return item;
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return list.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<T> listIterator(int index) {
        return list.listIterator(index);
    }

    @NonNull
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }


    public interface ListObserver<T>{
        void onItemRemoved(T item);
        void onItemAdded(int index, T element);
        void onItemSet(int index, T element, T newItem);
        void onItemAdded(T t, boolean result);
        void onItemRemoved(Object o, boolean result);
        void onCollectionAdded(Collection<? extends T> c, boolean result);
        void onCollectionAdded(int index, Collection<? extends T> c, boolean result);
        void onCollectionRemoved(Collection<?> c, boolean result);
        void onCollectionRetained(Collection<?> c, boolean result);
    }
}
