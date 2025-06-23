"use client"

import { useState } from "react"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Button } from "@/components/ui/button"
import { Badge } from "@/components/ui/badge"
import { Separator } from "@/components/ui/separator"
import { Calculator, RotateCcw, Scale, Ruler } from "lucide-react"
import { useToast } from "@/hooks/use-toast"
import { Toaster } from "@/components/ui/toaster"

interface BMIResult {
  value: number
  category: string
  color: string
  description: string
}

export default function BMICalculator() {
  const [weight, setWeight] = useState("")
  const [height, setHeight] = useState("")
  const [heightUnit, setHeightUnit] = useState<"m" | "cm">("cm")
  const [result, setResult] = useState<BMIResult | null>(null)
  const { toast } = useToast()

  const getBMICategory = (bmi: number): { category: string; color: string; description: string } => {
    if (bmi < 18.5) {
      return {
        category: "Underweight",
        color: "bg-blue-100 text-blue-800 border-blue-200",
        description: "BMI less than 18.5",
      }
    } else if (bmi >= 18.5 && bmi <= 24.9) {
      return {
        category: "Normal Weight",
        color: "bg-green-100 text-green-800 border-green-200",
        description: "BMI 18.5 - 24.9",
      }
    } else if (bmi >= 25.0 && bmi <= 29.9) {
      return {
        category: "Overweight",
        color: "bg-yellow-100 text-yellow-800 border-yellow-200",
        description: "BMI 25.0 - 29.9",
      }
    } else if (bmi >= 30.0 && bmi <= 34.9) {
      return {
        category: "Obesity Class I",
        color: "bg-orange-100 text-orange-800 border-orange-200",
        description: "BMI 30.0 - 34.9",
      }
    } else if (bmi >= 35.0 && bmi <= 39.9) {
      return {
        category: "Obesity Class II",
        color: "bg-red-100 text-red-800 border-red-200",
        description: "BMI 35.0 - 39.9",
      }
    } else {
      return {
        category: "Obesity Class III",
        color: "bg-red-200 text-red-900 border-red-300",
        description: "BMI 40.0 and above",
      }
    }
  }

  const validateInputs = (): boolean => {
    if (!weight.trim() || !height.trim()) {
      toast({
        title: "Input Error",
        description: "Please enter both weight and height values.",
        variant: "destructive",
      })
      return false
    }

    const weightNum = Number.parseFloat(weight)
    const heightNum = Number.parseFloat(height)

    if (isNaN(weightNum) || isNaN(heightNum)) {
      toast({
        title: "Invalid Input",
        description: "Please enter valid numeric values.",
        variant: "destructive",
      })
      return false
    }

    if (weightNum <= 0 || heightNum <= 0) {
      toast({
        title: "Invalid Values",
        description: "Weight and height must be positive values.",
        variant: "destructive",
      })
      return false
    }

    if (weightNum > 1000) {
      toast({
        title: "Invalid Weight",
        description: "Please enter a realistic weight value.",
        variant: "destructive",
      })
      return false
    }

    if (heightUnit === "cm" && (heightNum > 300 || heightNum < 50)) {
      toast({
        title: "Invalid Height",
        description: "Please enter a realistic height value (50-300 cm).",
        variant: "destructive",
      })
      return false
    }

    if (heightUnit === "m" && (heightNum > 3 || heightNum < 0.5)) {
      toast({
        title: "Invalid Height",
        description: "Please enter a realistic height value (0.5-3.0 m).",
        variant: "destructive",
      })
      return false
    }

    return true
  }

  const calculateBMI = () => {
    if (!validateInputs()) return

    const weightNum = Number.parseFloat(weight)
    let heightNum = Number.parseFloat(height)

    if (heightUnit === "cm") {
      heightNum = heightNum / 100
    }

    const bmi = weightNum / (heightNum * heightNum)
    const category = getBMICategory(bmi)

    setResult({
      value: Math.round(bmi * 10) / 10,
      category: category.category,
      color: category.color,
      description: category.description,
    })

    toast({
      title: "BMI Calculated",
      description: `Your BMI is ${Math.round(bmi * 10) / 10} (${category.category})`,
    })
  }

  const clearInputs = () => {
    setWeight("")
    setHeight("")
    setResult(null)
    toast({
      title: "Inputs Cleared",
      description: "All fields have been reset.",
    })
  }

  const toggleHeightUnit = () => {
    setHeightUnit((prev) => (prev === "cm" ? "m" : "cm"))
    setHeight("")
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 p-4">
      <div className="max-w-md mx-auto">
        <Card className="shadow-lg">
          <CardHeader className="text-center">
            <div className="flex justify-center mb-2">
              <div className="p-3 bg-blue-100 rounded-full">
                <Calculator className="h-8 w-8 text-blue-600" />
              </div>
            </div>
            <CardTitle className="text-2xl font-bold text-gray-800">BMI Calculator</CardTitle>
            <CardDescription>Calculate your Body Mass Index and health category</CardDescription>
          </CardHeader>

          <CardContent className="space-y-6">
            <div className="space-y-2">
              <Label htmlFor="weight" className="text-sm font-medium flex items-center gap-2">
                <Scale className="h-4 w-4" />
                Weight (kg)
              </Label>
              <Input
                id="weight"
                type="number"
                placeholder="Enter your weight"
                value={weight}
                onChange={(e) => setWeight(e.target.value)}
                className="text-lg"
                step="0.1"
                min="0"
              />
            </div>

            <div className="space-y-2">
              <Label htmlFor="height" className="text-sm font-medium flex items-center gap-2">
                <Ruler className="h-4 w-4" />
                Height ({heightUnit})
              </Label>
              <div className="flex gap-2">
                <Input
                  id="height"
                  type="number"
                  placeholder={`Enter height in ${heightUnit}`}
                  value={height}
                  onChange={(e) => setHeight(e.target.value)}
                  className="text-lg flex-1"
                  step={heightUnit === "cm" ? "1" : "0.01"}
                  min="0"
                />
                <Button type="button" variant="outline" onClick={toggleHeightUnit} className="px-3">
                  {heightUnit}
                </Button>
              </div>
            </div>

            <div className="flex gap-3">
              <Button onClick={calculateBMI} className="flex-1" size="lg">
                <Calculator className="h-4 w-4 mr-2" />
                Calculate BMI
              </Button>
              <Button onClick={clearInputs} variant="outline" size="lg">
                <RotateCcw className="h-4 w-4" />
              </Button>
            </div>

            {result && (
              <>
                <Separator />
                <div className="space-y-4">
                  <div className="text-center">
                    <h3 className="text-lg font-semibold text-gray-700 mb-2">Your BMI Result</h3>
                    <div className="text-4xl font-bold text-gray-800 mb-2">{result.value}</div>
                    <Badge className={`${result.color} text-sm px-3 py-1`}>{result.category}</Badge>
                    <p className="text-sm text-gray-600 mt-2">{result.description}</p>
                  </div>

                  <div className="bg-gray-50 p-4 rounded-lg">
                    <h4 className="font-semibold text-sm text-gray-700 mb-3">BMI Categories</h4>
                    <div className="space-y-2 text-xs">
                      <div className="flex justify-between">
                        <span>Underweight</span>
                        <span className="text-gray-600">{"< 18.5"}</span>
                      </div>
                      <div className="flex justify-between">
                        <span>Normal weight</span>
                        <span className="text-gray-600">18.5 - 24.9</span>
                      </div>
                      <div className="flex justify-between">
                        <span>Overweight</span>
                        <span className="text-gray-600">25.0 - 29.9</span>
                      </div>
                      <div className="flex justify-between">
                        <span>Obesity Class I</span>
                        <span className="text-gray-600">30.0 - 34.9</span>
                      </div>
                      <div className="flex justify-between">
                        <span>Obesity Class II</span>
                        <span className="text-gray-600">35.0 - 39.9</span>
                      </div>
                      <div className="flex justify-between">
                        <span>Obesity Class III</span>
                        <span className="text-gray-600">{"≥ 40.0"}</span>
                      </div>
                    </div>
                  </div>
                </div>
              </>
            )}
          </CardContent>
        </Card>

        <div className="text-center mt-6 text-sm text-gray-600">
          <p>BMI is a screening tool and not a diagnostic tool.</p>
          <p>Consult healthcare professionals for medical advice.</p>
        </div>
      </div>

      <Toaster />
    </div>
  )
}
