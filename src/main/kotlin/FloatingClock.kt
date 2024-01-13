/*
* Copyright (c) 2024 MohLovesCode
* Permission is hereby granted, free of charge, to any person obtaining a copy of
* this software and associated documentation files (the “Software”), to deal in the
* Software without restriction, including without limitation the rights to use, copy,
* modify, merge, publish, distribute, sublicense, and/or sell copies of the
* Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND,
* EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
* OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
* NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
* HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
* WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
* FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
* OTHER DEALINGS IN THE SOFTWARE.
* */

import java.awt.Point
import java.awt.Dimension
import java.awt.Component
import java.awt.Font
import java.awt.Color
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.SwingUtilities
import javax.swing.Timer
import java.text.SimpleDateFormat
import java.util.Date
import java.io.File
import javax.swing.ImageIcon
import kotlin.system.exitProcess

/*
* @author MohLovesCode
* GitHub https://github.com/MohLovesCode
* */

class FloatingClock : JFrame() {

    private var clockLabel: JLabel
    private var dateLabel: JLabel
    private var initialClick: Point? = null

    init {
        // Set frame properties
        val launcherIcon = ImageIcon("assets/icon/launcher-icon.png")
        iconImage = launcherIcon.image
        isUndecorated = true
        size = Dimension(300, 100)
        defaultCloseOperation = EXIT_ON_CLOSE

        // Use BoxLayout for the contentPane
        layout = BoxLayout(contentPane, BoxLayout.Y_AXIS)
        setLocationRelativeTo(null)

        // Create clock label
        clockLabel = JLabel()
        dateLabel = JLabel()
        clockLabel.alignmentX = Component.CENTER_ALIGNMENT
        dateLabel.alignmentX = Component.CENTER_ALIGNMENT

        // Load custom font
        val fontFile = File("assets/font/Ds-Digit.ttf")
        val baseFont = Font.createFont(Font.TRUETYPE_FONT, fontFile)
        val customFont = baseFont.deriveFont(Font.BOLD, 45f) // include Font.BOLD
        val simpleFont = Font("Arial", Font.PLAIN, 18)

        clockLabel.font = customFont
        dateLabel.font = simpleFont

        // Set text color for clockLabel and dateLabel
        clockLabel.foreground = Color.WHITE
        dateLabel.foreground = Color.WHITE

        updateClock()

        // Add spacing before clock label
        add(Box.createVerticalStrut(10))

        // Add clock label to the frame
        add(clockLabel)
        add(dateLabel)

        // Set frame behavior
        isAlwaysOnTop = true
        isResizable = false
        opacity = 0.7f // Set frame opacity

        // Set background color to black
        contentPane.background = Color.BLACK

        // Add mouse listener for dragging
        addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent) {
                initialClick = e.point
                getComponentAt(initialClick)
            }

            override fun mouseClicked(e: MouseEvent) {
                if (e.clickCount == 2) {
                    // Double-click event, close the program
                    dispose()
                    exitProcess(0)
                }
            }
        })

        addMouseMotionListener(object : MouseAdapter() {
            override fun mouseDragged(e: MouseEvent) {
                val thisX = location.x
                val thisY = location.y

                val xMoved = e.x - initialClick!!.x
                val yMoved = e.y - initialClick!!.y

                val x = thisX + xMoved
                val y = thisY + yMoved

                setLocation(x, y)
            }
        })

        // Update clock every second
        val timer = Timer(1000) { updateClock() }
        timer.start()

        // Set frame visibility
        isVisible = true
    }

    private fun updateClock() {
        val sdf = SimpleDateFormat("hh:mm aa")
        val formattedTime = sdf.format(Date())

        clockLabel.text = formattedTime
        dateLabel.text = SimpleDateFormat("EEEE, MMMM d, yyyy").format(Date())
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SwingUtilities.invokeLater { FloatingClock() }
        }
    }
}
