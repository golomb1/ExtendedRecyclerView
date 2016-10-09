package com.libs.golomb.exampleapp;

import com.libs.golomb.extendedrecyclerview.DataExtractor.SectionListDataExtractor;

/**
 * Created by tomer on 04/10/2016.
 * Representing a data string.
 */
public class SampleData implements SectionListDataExtractor.SectionElement<String>{

    private String string;

    public SampleData(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SampleData that = (SampleData) o;

        return string != null ? string.equals(that.string) : that.string == null;

    }

    @Override
    public int hashCode() {
        return string != null ? string.hashCode() : 0;
    }

    @Override
    public String toString() {
        return string;
    }

    @Override
    public String getSection() {
        return String.valueOf(string.charAt(0));
    }

    @Override
    public String getSectionName() {
        return getSection();
    }
}
