# snipCLIP
SNP filtering tool

SNPCLIP allows investigators to perform filtering on SNP data sets based on a set of statistical calculations. The user can then output the dataset into S.A.G.E. format for further processing. Currently, SNPCLIP allows investigators to filter based on missingness, allele frequency, pairwise linkage disequilibrium, and genome map regions.

## 1.1 Limitations

SNPCLIP currently supports SNPs with two alleles (or a missing value) at each location.

SNPCLIP's memory usage has been optimized for usage with SNP data. The internal data format is similar to that of PLINK. As an example of its effeciency, the program can store 19K SNPs over 162 individuals using only 50 MBs of RAM (Hapmap phase 3 data).

## 1.2 Theory

### 1.2.1 Dimension Reduction

Dimension Reduction is a term used to describe a filtering of a data set so that only relevant data remain. SNPCLIP reduces the dimension of a data set by using any of the available filters provided (missingness, allele frequency, etc.). By reducing the dimension (in this case, the number of SNPs) of a data set one can reduce elements of the data set that do not provide useful information or have undesireable attributes. Two major benefits to reducing the dimensionality of a data set is that the resulting data set contains only the data of interest, which in turn, reduces the amount of time to perform further analysis on the data set.

### 1.2.2 Filters

Filtering is the core of SNPCLIP. It is a form of dimension reduction that allows the user to specify restrictions on the data set to obtain useful SNPs. The term filtering is more appropriate in this setting because the user supplies a set of criteria that the data must consist of. All of the filters within SNPCLIP are INCLUSIVE, meaning that any SNP that meets all the criteria specified will remain in the filtered set.

#### 1.2.2.1 Missingness Filter

The missingness filter takes a simple count of how many individuals have missing data for a given SNP. If the percentage of missing individuals at a given SNP fall within the min and max values specified by the user, they will be included in the filtered data.

#### 1.2.2.2 Minor Allele Frequency Filter

The minor allele frequency filter counts the total number of occurrances of each allele for each SNP. It then selects the allele at each SNP with the lower count as the minor allele. If the percentage of minor alleles present at a given SNP falls within the min and max values specified by the user, then the SNP will be included in the filtered data.

#### 1.2.2.3 Linkage Disequilibrium Filter

The LD filter calculates the composite  LD between each pair of adajacent SNPs (Weir 1996). It then filters out SNPs that do not fall within the min and max specified by the user. Next, the LD filter enters a loop, comparing the remaining SNPs. The filter will continue to apply the filter recursively on the remaining SNPs until no change in the number of SNPs has occured between one run and the subsequent run.

#### 1.2.2.4 Genome Map Filter

The genome map filter filters the SNPs based upon the map specified by the user and the region(s) and SNP(s) specified by the user in the map location dialog.
