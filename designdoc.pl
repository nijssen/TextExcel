#!/usr/bin/perl

use strict;
use warnings;
use List::Util qw/first/;

open OUT, ">", "doc.html" or die $!;

foreach(@ARGV) {
	#Read file
	open FILE, "<", $_ or die $!;
	my @lines = <FILE>;
	
	#Find class name
	my @cl =
		map { split /\s+/, $_  }
		grep { /class/ }
		@lines;
	my $cidx = first { $cl[$_] eq "class" } 0..$#cl;
	my $classname = $cl[$cidx + 1] || die $!;
	
	my @variables = ();
	my @functions = ();
	my $desc = "";
	
	#Find parts of class
	foreach(@lines) {
		s/^\s+//g;
		if($_ !~ /^(public|protected|private)/) {
			next unless /^\*/;
			s/\*//g;
			s/@(.*?)//g;
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
				join ", ", @paramtypes,
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
		foreach(sort { $a->[0] cmp $b->[1] } @$ref) {
			my @t = @$_;
			print OUT "<tr>";
			print OUT (map {"<td>" . $_ . "</td>" } @t);
			print OUT "</tr>\n";
		}
	}
	
	print OUT "</table>\n\n";
		
}

sub firstindex {
	my $search = shift;

	my %idx;
	@idx{@_} = (0..$#_);

	return $idx{$search};
}
