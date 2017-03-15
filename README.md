# PotatoOnMars

Potato On Mars is a daemon created to monitor server load and detect anomalies on the time series by compraing regular patterns recorded and new patterns.

## How does it work
Potato On Mars simply record a lot of data to create its data base for regular patterns. 

Regular patterns are discretized in 24-letters-long words with one letter for each hourwith the HotSax algorithm

Then when a new data comes out the software makes a new 24h-long pattern with 23 other data points, discretizes it and simply computes an euclidian distance between regular patterns and the new pattern.

The distance is then compared to a threshold. If it is greater then an alert is raised.

## What was used for the analysis

We used different mathematical methods related to time series in our analysis phase.

### Potato On Mars is based on :
* [GrammarViz](https://github.com/GrammarViz2)
* [Sax / HotSax algorithm](https://github.com/jMotif/SAX)

Name approved by Mark Watney
<img src="http://lynncinnamon.com/wp-content/uploads/2015/09/martianpotatoes.jpg"> </img>
