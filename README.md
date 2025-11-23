# Simple Minds Quiz Application

A modern, interactive Java-based quiz application with an enhanced graphical user interface.

##  GUI Improvements Made

### 1. **Modern Design & Styling**
- **Gradient Backgrounds**: Beautiful blue gradient backgrounds throughout the application
- **Rounded Corners**: Modern rounded rectangles for panels and buttons
- **Shadow Effects**: Text shadows for better readability and visual appeal
- **Professional Color Scheme**: Consistent color palette using modern web colors

### 2. **Enhanced Typography**
- **Modern Fonts**: Replaced old fonts with Segoe UI for better readability
- **Improved Font Sizes**: Better hierarchy and spacing
- **Better Text Formatting**: Enhanced HTML formatting for rules and questions

### 3. **Improved Layout & Components**
- **Better Spacing**: Improved margins and padding throughout
- **Organized Panels**: Logical grouping of related elements
- **Enhanced Buttons**: Styled buttons with hover effects and rounded corners
- **Modern Input Fields**: Better styled text input with borders and focus states

### 4. **Visual Enhancements**
- **Semi-transparent Overlays**: Better text readability over background images
- **Decorative Elements**: Subtle decorative circles and visual accents
- **Improved Icons**: Better image scaling and positioning
- **Professional Borders**: Subtle borders and shadows for depth

### 5. **User Experience Improvements**
- **Input Validation**: Name validation before proceeding (letters and spaces only)
- **Confirmation Dialogs**: Exit confirmation dialog
- **Better Feedback**: Performance-based messages, color coding, and immediate answer feedback
- **Improved Navigation**: Better button placement, sizing, and keyboard shortcuts
- **Pause/Resume**: Ability to pause the quiz at any time
- **Progress Tracking**: Visual progress bar with percentage completion
- **Timer Warnings**: Visual and color-coded timer warnings as time runs out
- **Auto-advance**: Automatic progression when time expires

##  Features

### **Core Functionality**
- **10 Multiple-Choice Questions**: Java programming questions
- **15-Second Timer**: Per question with visual warnings
- **Scoring System**: 10 points per correct answer (100 points total)
- **50-50 Lifeline**: Remove two incorrect options (one-time use)
- **Question Randomization**: Questions appear in random order each session
- **Pause/Resume**: Pause the quiz at any time
- **Immediate Feedback**: See if your answer is correct immediately after submitting
- **Progress Tracking**: Visual progress bar showing completion percentage
- **Auto-advance**: Automatically moves to next question when time expires
- **Keyboard Shortcuts**: Enter key support for quick navigation

##  Screen Features

### **Login Screen**
- Modern gradient background
- Professional input field styling
- Validation for empty names and invalid characters (letters and spaces only)
- Enter key support for quick navigation
- Exit confirmation dialog

### **Rules Screen**
- Enhanced rules display with better formatting
- Modern card-style layout
- Improved readability with HTML formatting
- Professional button styling
- Keyboard shortcuts (Enter to start, Escape to go back)

### **Quiz Interface**
- Large, clear question display
- Styled radio button options with hover effects
- Modern timer display with visual warnings (color changes and blinking when time is running out)
- Progress bar showing completion percentage
- Real-time score tracking
- Pause/Resume functionality
- Immediate feedback on answers (shows correct/incorrect after each question)
- Auto-advance on timeout
- Question randomization for each session
- Better organized button layout
- Enhanced visual hierarchy

### **Score Display**
- Performance-based feedback messages
- Color-coded score display
- Modern card-style score panel
- Encouraging messages based on performance
- Detailed results with percentage and questions answered
- Integrated results screen (shown directly after quiz completion)

##  Technical Improvements

- **Java Swing**: Enhanced with modern styling techniques
- **Graphics2D**: Custom painting for gradients and rounded shapes
- **Event Handling**: Improved user interaction with keyboard and mouse events
- **Layout Management**: Better component organization using null layout for precise positioning
- **Responsive Design**: Better window positioning and sizing
- **Timer Management**: Swing Timer for countdown with pause/resume functionality
- **Randomization**: Fisher-Yates shuffle algorithm for question randomization
- **State Management**: Proper handling of quiz state, score, and user answers

##  How to Run

1. **Compile the Java files**:
   ```bash
   javac -cp . src/quiz/application/*.java
   ```

2. **Run the application**:
   ```bash
   java -cp src quiz.application.Login
   ```

3. **Or run individual screens** (for testing):
   ```bash
   java -cp src quiz.application.Rules "Your Name"
   java -cp src quiz.application.Quiz "Your Name"
   java -cp src quiz.application.Score "Your Name" 85
   ```
   
   **Note**: The normal flow is Login → Rules → Quiz → Results. The Quiz class shows results directly after completion. The Score class is a standalone component that can be used separately if needed.

##  Color Palette Used

- **Primary Blue**: `#4682B4` (Steel Blue)
- **Dark Blue**: `#191970` (Midnight Blue)
- **Success Green**: `#2ECC71` (Emerald)
- **Warning Orange**: `#F39C12` (Orange)
- **Danger Red**: `#E74C3C` (Alizarin)
- **Info Blue**: `#3498DB` (Blue)
- **Purple**: `#9B59B6` (Amethyst)

##  System Requirements

- **Java**: JDK 8 or higher
- **Operating System**: Windows, macOS, or Linux
- **Memory**: Minimum 512MB RAM
- **Display**: 1024x768 or higher resolution

##  Customization

The application can be easily customized by modifying:
- Color schemes in the gradient definitions
- Font families and sizes
- Button styles and hover effects
- Panel layouts and positioning
- Background images and overlays

##  Notes

- All GUI improvements maintain the original functionality
- Enhanced visual appeal without compromising performance
- Modern design principles applied throughout
- Better accessibility with improved contrast and readability
- Results are displayed directly in the Quiz class after completion
- The Score class is available as a standalone component but is not used in the main flow
- Timer provides visual feedback: normal (red), warning (orange at ≤10s), critical (blinking red at ≤5s)
- All questions are Java programming related
- Name validation ensures only letters and spaces are accepted

---

**Developed with ❤️ using Java Swing and modern UI design principles**
