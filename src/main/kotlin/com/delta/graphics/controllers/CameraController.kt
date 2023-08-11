package com.delta.graphics.controllers

import com.badlogic.gdx.InputAdapter
import com.delta.graphics.geometry.Camera

// camera.position
class CameraController(
    private val camera: Camera
) : InputAdapter() {

    private val lastPosition = camera.getPosition()
    private var positionOnTouch = Pair(0, 0)

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        positionOnTouch = screenX to screenY
        lastPosition.set(camera.screenToWorld(screenX, screenY))
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        val newPosition = camera.screenToWorld(screenX, screenY)
        camera.translate(lastPosition)
        val differenceVectors = lastPosition.sub(newPosition)
        //camera.updatePosition(differenceVectors)
        camera.translate(differenceVectors)
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return positionOnTouch!= screenX to screenY
    }
}