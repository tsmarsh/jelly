# jelly

A Clojure library designed to take MIT election data and make it... worse?

## Usage

There are a bunch of namespaces for the various documents, each of them has a 'create-files' function. Running them will generate the data.

The data is available in this repo in the 'data' directory. 

## Why does this suck?

It supposed to simulate a moderately sized, poorly designed service mesh with the following features:

* Data isn't shared across document types
* id are used as references
* there are aggregate types
* there are aggregate keys (yum)

Its that jelly that fills the donut. 
