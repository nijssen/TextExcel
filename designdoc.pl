#!perl

use strict;
use warnings;

open OUT, ">", "doc.html" or die $!;

foreach(@ARGV) {
	#Read file
	open FILE, "<", $_ or die $!;
	my @lines = <FILE>;
	
	#Find class name
	my $classname = (
		map { (split /\s/, $_)[1] }
		grep { /class (.*?)/ } @lines
	)[0];
	
	my @variables = ();
	my @functions = ();
	my $desc = "";
	
	#Find parts of class
	foreach(@lines) {
		if($_ !~ /(public|protected|private)/) {
			s/\*//;
			$desc .= $_;
			next;
		}
		next if /public class/;
		chomp;		
		
		my @parts = grep { length $_ > 0 }
					split /\s+/, $_;
		
		my $access = shift @parts;
		
		shift @parts while $parts[0] =~ /(static|final)/;
		
		my $type = shift @parts;
		my $name = shift @parts;
		
		if((index $name, "(") + 1) { #Non-zero if found
			#function found
			
			/\((.*?)\)/;
			my @params = split ',', $1;
			my @paramtypes = map { $_ * 2 } @params / 2;
			
			push @functions, [
				(split /\Q(\E/, $name)[0], #name
				$type,
				\@paramtypes,
				$desc
			];
		} else {
			#variable found
			push @variables, [
				$name,
				$type,
				$desc
			];
		}
		
		$desc = "";
	}
	close FILE;
	
	printinfo($classname, Variables => \@variables, Functions => \@functions);
}

close OUT;
		
sub printinfo {
	my $classname = shift;
	my %things = @_;
	
	print OUT "<h1>$classname</h1><table>\n";
	
	foreach(sort keys %things) {
		my $ref = $things{$_};
		
		print OUT "<tr><th colspan=3>$_</th></tr>";
		foreach(sort { $a->[0] <=> $b->[1] } @$ref) {
			my @t = @$_;
			print OUT "<tr>";
			print OUT (map { + "<td>", $_, "</td>" } @t);
			print OUT "</tr>\n";
		}
	}
	
	print OUT "</table>\n\n";
		
}