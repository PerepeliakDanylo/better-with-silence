# Better With Silence

Tired of standing next to a Nether Portal and going deaf? Or the endless water and lava ambience drowning out everything else?

**So I made a mute button.**

Better With Silence adds the **Acoustic Dampener** — a craftable block that completely silences all sounds within an 8-block radius when placed. Right-click to toggle it on or off. Hostile mob sounds (creepers, zombies, skeletons, etc.) always pass through so you never miss incoming danger.

## Features

- **Complete sound silencing** — every sound within 8 blocks is cancelled (not just reduced)
- **Toggle on/off** — right-click the block to enable or disable dampening, with a click sound for feedback
- **Active by default** — starts working the moment you place it
- **Hostile mobs excluded** — creeper hisses, zombie groans, skeleton rattles and other hostile mob sounds always play through
- **Mineable by axe** — breaks faster with better axes, just like any wooden block

## Crafting Recipe

```
P W P
W S W
P W P
```

| Symbol | Item |
|--------|------|
| P | Oak Planks |
| W | Wool |
| S | Slimeball |

## Dependencies

- [Babric](https://github.com/Turnip-Labs/babric-instance-fabric/releases) (Fabric loader for BTA)
- [HalpLibe](https://github.com/Turnip-Labs/bta-halplibe/releases) 5.4.0+

## Installation

1. Download the latest JAR from [Releases](../../releases)
2. Place it in your BTA instance's `.minecraft/mods/` folder
3. Launch the game

## Localization

The mod ships with English (`en_US`) localization. A Ukrainian (`uk_UA`) translation file is included in the repository root for use with external language packs.

## Building from Source

### Prerequisites
- JDK 21+ ([Eclipse Temurin](https://adoptium.net/temurin/releases/) recommended)

### Build
```sh
./gradlew build
```
The compiled JAR will be in `build/libs/`.

## License

[MIT](LICENSE)
