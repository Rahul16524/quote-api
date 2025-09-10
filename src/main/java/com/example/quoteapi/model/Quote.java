package com.example.quoteapi.model;

public class Quote {
	private String quote;

    public Quote() {
        // Default constructor for JSON serialization
    }

    public Quote(String quote) {
        this.quote = quote;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }
}
