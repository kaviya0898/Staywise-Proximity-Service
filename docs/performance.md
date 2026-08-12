# Performance Optimization

## Google Places API

### Problem

Initially, 15 Google Places API calls were executed sequentially.

### Sequential Latency

~27,151 ms

### Solution

Used `CompletableFuture` to execute independent I/O-bound API calls in parallel.

### Parallel Latency

334 ms

### Result

~27.15 sec → ~334 ms
~98.8% latency reduction
