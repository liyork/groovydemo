package com.wolf.inaction.packagetest.business
// multiple class declarations in one file
class Vendor1 {
    public String name
    public String product
    public Address address = new Address()
}

class Address {
    public String street, town, state
    public int zip
}

def canoo = new Vendor1()
canoo.name = 'Canoo Engineering AG'
canoo.product = 'UltraLightClient (ULC)'
canoo.address.street = 'Kir'
canoo.address.zip = 4051
canoo.address.town = 'base1'
canoo.address.state = 'switzerland'
assert canoo.dump() =~ /ULC/
assert canoo.address.dump() =~ /ase1/


