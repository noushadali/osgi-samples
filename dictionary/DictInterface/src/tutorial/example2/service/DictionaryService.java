/*
 * OSGi and Gravity Service Binder tutorial.
 * Copyright (c) 2003  Richard S. Hall
 * http://oscar-osgi.sourceforge.net
**/

package tutorial.example2.service;

/**
 * A simple service interface that defines a dictionary service.
 * A dictionary service simply verifies the existence of a word.
**/
public interface DictionaryService
{
    /**
     * Check for the existence of a word.
     * @param word the word to be checked.
     * @return true if the word is in the dictionary,
     *         false otherwise.
    **/
    public boolean checkWord(String word);
}
