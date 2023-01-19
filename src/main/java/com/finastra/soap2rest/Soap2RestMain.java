package com.finastra.soap2rest;


import com.finastra.soap2rest.parser.WSDLParser;

public class Soap2RestMain {
    public static void main(String[] args) {
        WSDLParser wp = new WSDLParser();
        wp.readWSDL();
    }
}


