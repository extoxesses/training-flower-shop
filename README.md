# Flower Shop

### Context
A flower shop used to base the price of their flowers on an item by item cost. So if a customer ordered 10 roses then they would be charged 10x the cost of single rose. The flower shop has decided to start selling their flowers in bundles and charging the customer on a per bundle basis. So if the shop sold roses in bundles of 5 and 10 and a customer ordered 15 they would get a bundle of 10 and a bundle of 5.

The flower shop currently sells the following products:

Name   | Code | Bundle
---    | ---  | ---
Roses  | R12  | 5 @ $6.99<br/>10 @ $12.99
Lilies | L09  | 3 @ $9.95<br/>6 @ $16.95<br/>9 @ $24.95
Tulips | T58  | 3 @ $5.95<br/>5 @ $9.95<br/>9 @ $16.99

### Task:
Given a customer order you are required to determine the cost and bundle breakdown for each product. To save on shipping space each order should contain the minimal number of bundles.

### Input:
Each order has a series of lines with each line containing the number of items followed by the product code

An example input:
```
10 R12
15 L09
13 T58
```

### Output:
A successfully passing test(s) that demonstrates the following output: (The format of the output is not important)
```
10 R12 $12.99
! 1 x 10 $12.99
15 L09 $41.90
! 1 x 9 $24.95
! 1 x 6 $16.95
13 T58 $25.85
! 2 x 5 $9.95
! 1 x 3 $5.95
```

## Solution
The proposed solution is described [here](.\docs\solution.md).