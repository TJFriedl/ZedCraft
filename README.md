# ZedCraft

![ZedCraft Logo](README/ZedCraft.PNG)

## Overview

ZedCraft is a project developed as part of CPRE 488, also known as "Embedded Systems Design," where we aim to run a Minecraft server on a ZedBoard FPGA. The project integrates various peripherals, including a camera and a turret from our CPRE 488 lab materials, to enable interaction with the Minecraft environment through plugins and C programs.

## Features

- Run a Minecraft server on a ZedBoard FPGA.
- Integrate a camera for in-game visuals.
- Connect a turret for interactive gameplay.
- Develop Minecraft plugins and C programs for peripheral interaction.
- Explore the possibilities of embedded systems in gaming environments.

## Getting Started

### Prerequisites

- [ZedBoard FPGA](https://digilent.com/shop/zedboard-zynq-7000-arm-fpga-soc-development-board/)
- [Minecraft Server - Spigot 1.8 API Documentation](https://helpch.at/docs/1.8/)
- [(CPRE 488 MP-2): Camera module lab](https://class.ece.iastate.edu/cpre488/labs/MP-2.pdf)
- [(CPRE 488 MP-2): Camera module materials](https://class.ece.iastate.edu/cpre488/labs/MP-2.zip)
- [(CPRE 488 MP-3): Turret hardware lab](https://class.ece.iastate.edu/cpre488/labs/MP-3.pdf)
- [(CPRE 488 MP-3): Turret hardware materials](https://class.ece.iastate.edu/cpre488/labs/MP-3.zip)

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/TJFriedl/ZedCraft.git
   ```

2. Follow the plugin setup instructions in the [Plugin Setup Documentation](docs/PluginSetup.pdf).
3. Read our Final Project Report located at [ZedCraft Final Report Documentation](docs/ZedCraft.pdf)

## Backdoor.sh and Server.sh Usage

1. Start the Minecraft server by being in the "server" directory of the SD card (cd /mnt/<sd-card-partition>/server) and running the command "./../server.sh" (this should be don as a background process or in a seperate terminal instance using the screen command.
2. Wait for the server to completely bootup and give a "Done" command.
3. From the same directory as above, run the command "./../backdoor.sh". This should NOT be done as a background process or should be done in a seperate terminal window using screen.
4. The Minecraft Text Chat will not have new features as outlined below
   i. Starting a chat message with ">>" will run it as a bash script
   ii. Starting a chat message with ">>!" will be a character to send to the NERF turret we are using (i.e ">>!10" will run the linux command "echo –en ‘\x10’ > /dev/launcher0")
   iii. Starting a chat message with ">>0" will run the command as a MINECRAFT command as an adminitstator. (i.e. ">>0kill all" will kill all living entities in the minecraft world)

## Acknowledgements

- Special thanks to our instructor, TAs, and classmates for their support and feedback.
