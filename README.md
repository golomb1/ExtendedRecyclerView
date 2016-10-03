# ExtendedRecyclerView

This library was made in order to simplify the creation of a list using a recyclerview.
Using this library, it is simple to add header and footer, supporting swapping, dragging and scrolling.
This library use the RecyclerView-FastScroll that can be found here:
https://github.com/timusus/RecyclerView-FastScroll

# Usage
To use this library there is a need to implement the following classes:

DataExtractor
This class help arragned the data in a simple way, if you need full control you can implement the DataExtractor interface the way you want.
But for creating a list extending the ListDataExtractor class, note that this class doesn't support the full capability of the RecyclerView-FastScroll, so in order to add it, extend this class and override the method:

String getElementSectionName(T item);

IViewHolderGenerator
This class returns the viewholder according to type for each item.
The method generate return a viewholder accordint to the type of the element.
also this class responsible for binding the empty state of the list, where the list doesn't have items.

There is a need to implement the relevant viewholder by extending the class ExtendedViewHolder
