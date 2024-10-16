
# TEME - Transmission Electron Microscopy (TEM) Statistical Analysis

## Introduction

**TEME** is an evolving software project aimed at providing a comprehensive tool for the **statistical analysis of Transmission Electron Microscopy (TEM) images**, with a particular focus on analyzing **plasmon interactions** and other nanoscale phenomena. The project aims to enable researchers to perform advanced image processing and analysis on TEM images while providing an intuitive **Blender-like interface** for user interaction.

This tool is currently in the **early development stages**, but it's designed to allow contributions from developers, scientists, and enthusiasts interested in TEM image analysis, scientific software development, and 3D visualization. Contributions are welcome to help bring this vision to reality.

**Placeholders for images:**
- ![Sample TEM Image](path/to/sample-tem-image.png) 
- ![Interface Overview](path/to/interface-overview.png) 
- ![Zooming and Scaling in Action](path/to/zoom-scale-action.png)

---

## What TEME Will Become

The vision of TEME is to offer a complete suite of tools for:
- **Image processing of TEM datasets**: Applying filters, extracting features, and generating quantitative data from microscopic images.
- **Plasmon analysis**: Statistical tools for analyzing plasmonic features within TEM images.
- **3D Visualization**: Providing a 3D interface for interacting with and manipulating images, similar to popular 3D design tools like Blender.
- **Measurement Tools**: Tools for placing and interacting with **scales**, allowing precise measurements of distances and areas at the nanoscale level.

Currently, many foundational features have been implemented, including view manipulation (pan, zoom, rotate), image handling, and scale manipulation. However, several key aspects such as advanced statistical tools and complete image processing functionalities are still in development.

---

## Project Structure and Eclipse Setup

TEME is developed as a **Java-based project** within the **Eclipse IDE**. Here’s how you can set it up:

### Prerequisites
- **Java 1.8 or higher** (The project does not run correctly with Java 1.7 due to cursor handling issues.)
- **Eclipse IDE** (or another Java IDE of your choice)
- **Gradle** (for project build management)

### Installation Steps
1. **Clone the repository**:
   ```bash
   git clone https://github.com/IstiCusi/TEME.git
   cd TEME
   ```

2. **Import into Eclipse**:
   - Open Eclipse.
   - Select `File > Import > Existing Gradle Project`.
   - Navigate to the directory where you cloned the repository, and Eclipse will import the project.

3. **Build and Run**:
   - Use the built-in Gradle wrapper to build the project.
   - Run the project within Eclipse to start interacting with the TEM images.

**Placeholders for project setup images:**
- ![Eclipse Project Setup](path/to/eclipse-setup.png)

---

## Software Design

TEME follows a well-organized **Model-View-Controller (MVC) architecture**:
- **Model**: The data, including TEM datasets, is encapsulated in objects like `TEMAllied`.
- **View**: The graphical components (`TEMView`, `TEMStatusBar`, `TEMInspectionPanel`) render the data and allow the user to interact with the images.
- **Controller**: `TEMAdapter` acts as the bridge between user inputs (keyboard/mouse) and the view, executing operations like panning, zooming, rotating, and editing.

### Key Design Elements:
  

1. **State Management** (`TEMViewState.java`):
   - Manages view properties such as scaling, panning, and rotation.
   - Ensures a smooth user experience by tracking these parameters and making the view responsive to input.

2. **Interaction Handlers** (`TEMAdapter.java`):
   - Handles user inputs via mouse and keyboard, delegating actions like zooming, panning, and rotating to the view.
   - Implements different modes for interacting with the TEM images (e.g., Scale Mode, Point Mode).

3. **Scale Management** (`ScaleAnimationTimerListener.java`, `TEMEditMode.java`, `TEMEditType.java`):
   - Implements animation for scale transitions, providing smooth zooming and scaling.
   - `TEMEditMode` and `TEMEditType` manage the various modes users can switch between (e.g., editing points or scales).

4. **View Components**:
   - **`TEMInspectionPanel.java`**: Contains the core view and inspection components, including navigation buttons, status bar, and editing mode selectors.
   - **`TEMStatusBar.java`**: Displays real-time information such as the mouse cursor position and other relevant details during image inspection.

---

## Controls

### Keyboard and Mouse Controls:

- **Mouse Controls**:
  - **Pan**: Middle mouse button to pan across the image.
  - **Zoom**: Use the mouse wheel to zoom in and out.
  - **Rotate**: Right-click and drag to rotate the 3D view.

- **Keyboard Controls**:
  - **Home**: Centers the view.
  - **Page Up / Page Down**: Switches between TEM images.
  - **Tab / Shift + Tab**: Switches between editing modes.
  - **Insert**: Creates a new scale in Scale Mode.

---

## Open Tasks and Future Development

Several key tasks are still in progress. Some of these are listed in the **TODOs.txt** file:

- **Implement Chain of Responsibility pattern** for more flexible input handling.
- **Refine zooming functionality** with Swing components and AffineTransform.
- **Finalize Scale Mode** and ensure accurate scale handling across different systems.
- **Advanced statistical tools** for image analysis.
- **More comprehensive unit tests** to ensure robustness.

---

## Open Source License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

### External Libraries
- **JTattoo**: Used for enhanced UI themes. JTattoo provides an attractive look and feel for Swing applications, but note that for open-source projects, the JTattoo license allows free usage without requiring the commercial license.

For further details, refer to [JTattoo License](http://www.jtattoo.net/License.html).
